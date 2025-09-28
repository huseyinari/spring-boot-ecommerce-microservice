package tr.com.huseyinari.ecommerce.product.service;

import feign.FeignException;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.ecommerce.product.client.CategoryClient;
import tr.com.huseyinari.ecommerce.product.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.enums.ProductStatus;
import tr.com.huseyinari.ecommerce.product.exception.ProductAlreadyExistException;
import tr.com.huseyinari.ecommerce.product.exception.ProductNotFoundException;
import tr.com.huseyinari.ecommerce.product.kafka.producer.ProductKafkaProducer;
import tr.com.huseyinari.ecommerce.product.mapper.ProductMapper;
import tr.com.huseyinari.ecommerce.product.projection.MostInspectedProductProjection;
import tr.com.huseyinari.ecommerce.product.repository.ProductRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductSearchParameters;
import tr.com.huseyinari.ecommerce.product.response.*;
import tr.com.huseyinari.ecommerce.product.shared.response.CategorySearchResponse;
import tr.com.huseyinari.springweb.rest.RequestUtils;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Validated
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;
    private final ProductKafkaProducer kafkaProducer;
    private final CategoryClient categoryClient;
    private final ProductInspectService productInspectService;
    private final ProductImageService productImageService;
    private final ECommerceConfigurationProperties configurationProperties;

    private String storageObjectContentUrl;

    @PostConstruct
    public void init() {
        storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();
    }

    @Transactional(readOnly = true)
    public ProductSearchPageableResponse search(ProductSearchParameters params, Pageable pageable) {
        Page<Product> result = this.repository.findAll(pageable);
        return ProductMapper.toSearchPageableResponse(result, this.storageObjectContentUrl);
    }

    @Transactional(readOnly = true)
    public ProductSearchResponse findById(String id) {
        Product product = this.repository.findById(id).orElseThrow(ProductNotFoundException::new);

        return ProductMapper.toSearchResponse(product, this.storageObjectContentUrl);
    }

    @Transactional(readOnly = true)
    public ProductSearchResponse findBySkuCode(String skuCode) {
        Product product = this.repository.findBySkuCode(skuCode).orElseThrow(ProductNotFoundException::new);

        return ProductMapper.toSearchResponse(product, this.storageObjectContentUrl);
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(item -> ProductMapper.toSearchResponse(item, this.storageObjectContentUrl))
                .toList();
    }

    @Transactional
    public ProductCreateResponse create(@Valid ProductCreateRequest request) {
        final String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow(() -> new RuntimeException("Kullanıcı bilgisi bulunamadı !"));

        if (this.repository.findByName(request.name()).isPresent()) {
            throw new ProductAlreadyExistException();
        }

        try {
            SinhaRestApiResponse<CategorySearchResponse> response = this.categoryClient.findOne(request.categoryId());
        } catch (FeignException.NotFound exception) {
            throw new RuntimeException("Geçerli bir kategori seçiniz !");
        } catch (Exception exception) {
            throw new RuntimeException("Kategori servisine erişilemedi. Lütfen daha sonra tekrar deneyiniz.");
        }

        Product product = ProductMapper.toEntity(request);
        product.setSkuCode(this.generateSkuCode(product.getName()));
        product.setUserId(currentUserId);
        product.setStatus(ProductStatus.PENDING);

        if (request.discount() == null || BigDecimal.ZERO.compareTo(request.discount()) == 0) {
            product.setDiscount(BigDecimal.ZERO);
            product.setDiscountedPrice(request.price());
        } else {
            product.setDiscountedPrice(request.price().subtract(request.discount()));
        }

        product = this.repository.save(product);

        this.kafkaProducer.createOpeningProductStock(product);

        logger.info("ID: {} -> Product başarıyla oluşturuldu.", product.getId());

        return ProductMapper.toCreateResponse(product);
    }

    public List<ProductMostInspectedTodayResponse> getMostInspectedTodayProducts() {
        List<MostInspectedProductProjection> result = this.productInspectService.getMostInspectedProductsToday();
        return result
                .stream()
                .map(item -> {
                    ProductSearchResponse product = this.findById(item.getProductId());

                    List<ProductImageSearchResponse> productImageList = this.productImageService.findByProductId(product.id());
                    String firstImageUrl = !productImageList.isEmpty() ? productImageList.get(0).imageUrl() : null;

                    return new ProductMostInspectedTodayResponse(
                        product.id(),
                        product.name(),
                        product.price(),
                        product.discount(),
                        product.discountedPrice(),
                        firstImageUrl,
                        item.getViewCount()
                    );
                })
                .toList();
    }

    private String generateSkuCode(String productName) {
        if (productName == null || productName.equals("")) {
            throw new RuntimeException("Ürün ismi boş olamaz !");
        }
        return productName.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "_");
    }
}

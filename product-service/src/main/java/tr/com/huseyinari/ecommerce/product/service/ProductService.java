package tr.com.huseyinari.ecommerce.product.service;

import feign.FeignException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.enums.ProductStatus;
import tr.com.huseyinari.ecommerce.product.exception.ProductAlreadyExistException;
import tr.com.huseyinari.ecommerce.product.exception.ProductNotFoundException;
import tr.com.huseyinari.ecommerce.product.kafka.producer.ProductKafkaProducer;
import tr.com.huseyinari.ecommerce.product.mapper.ProductMapper;
import tr.com.huseyinari.ecommerce.product.projection.MostInspectedProductProjection;
import tr.com.huseyinari.ecommerce.product.repository.ProductRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeValueCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductSearchParameters;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantValueCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.*;
import tr.com.huseyinari.ecommerce.product.shared.response.CategorySearchResponse;
import tr.com.huseyinari.springweb.rest.RequestUtils;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;
import tr.com.huseyinari.utils.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Validated
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ProductKafkaProducer kafkaProducer;
    private final CategoryClient categoryClient;
    private final ProductInspectService productInspectService;
    private final ProductImageService productImageService;
    private final ProductAttributeValueService productAttributeValueService;
    private final ProductVariantValueService productVariantValueService;
    private final ProductVariantIndexService productVariantIndexService;

    @PersistenceContext
    private final EntityManager entityManager;

//    @Transactional(readOnly = true)
//    public ProductSearchPageableResponse search(ProductSearchParameters params, Pageable pageable) {
//        Page<Product> result = this.repository.findAll(pageable);
//        return this.mapper.toSearchPageableResponse(result);
//    }

    @Transactional(readOnly = true)
    public ProductSearchResponse findById(String id) {
        Product product = this.repository.findById(id).orElseThrow(ProductNotFoundException::new);

        return this.mapper.toSearchResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductSearchResponse findBySkuCode(String skuCode) {
        Product product = this.repository.findBySkuCode(skuCode).orElseThrow(ProductNotFoundException::new);

        return this.mapper.toSearchResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDetailSearchResponse findDetailById(String id) {
        ProductSearchResponse productSearchResponse = this.findById(id);

        ProductDetailSearchResponse response = new ProductDetailSearchResponse();
        response.setId(productSearchResponse.id());
        response.setName(productSearchResponse.name());
        response.setDescription(productSearchResponse.description());
        response.setPrice(productSearchResponse.price());
        response.setDiscount(productSearchResponse.discount());
        response.setDiscountedPrice(productSearchResponse.discountedPrice());

        // product images
        List<ProductImageSearchResponse> productImageList = this.productImageService.findByProductId(id);
        response.setImageUrls(
            productImageList
                .stream()
                .map(ProductImageSearchResponse::imageUrl)
                .toList()
        );

        // product attribute values
        List<ProductAttributeValueSearchResponse> productAttributeValues = this.productAttributeValueService.findAllByProductIdOrderByProductAttributeId(id);
        response.setAttributeValues(productAttributeValues);

        // product variant values
        List<ProductVariantValueSearchResponse> productVariantValues = this.productVariantValueService.findAllByProductIdOrderByProductVariantId(id);
        response.setVariantValues(productVariantValues);

        // product variant index
        List<ProductVariantIndexSearchResponse> productVariantIndexes = this.productVariantIndexService.findByProductId(id);
        response.setVariantIndexes(productVariantIndexes);

        return response;
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

        Product product = this.mapper.toEntity(request);
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

        final ProductCreateResponse productCreateResponse = this.mapper.toCreateResponse(product);

        // product attribute values
        if (CollectionUtils.isNotEmpty(request.attributeValues())) {
            final Product finalProduct = product;
            List<ProductAttributeValueCreateRequest> attributeValues = request.attributeValues()
                    .stream()
                    .map(productAttributeValue -> {
                        // Kaydedilen ürün'ün id'si attribute value listesindeki item'lara ekleniyor.
                        return new ProductAttributeValueCreateRequest(productAttributeValue.productAttributeId(), finalProduct.getId(), productAttributeValue.attributeValue());
                    })
                    .toList();

            List<ProductAttributeValueCreateResponse> attributeValueCreateResponseList = this.productAttributeValueService.createOrUpdateAll(attributeValues);
            productCreateResponse.setAttributeValues(attributeValueCreateResponseList);
        }

        // product variant values
        if (CollectionUtils.isNotEmpty(request.variantValues())) {
            final Product finalProduct = product;
            List<ProductVariantValueCreateRequest> variantValues = request.variantValues()
                    .stream()
                    .map(productVariantValue -> {
                        // Kaydedilen ürün'ün id'si variant value listesindeki item'lara ekleniyor.
                        return new ProductVariantValueCreateRequest(productVariantValue.productVariantId(), finalProduct.getId(), productVariantValue.variantValue());
                    })
                    .toList();

            List<ProductVariantValueCreateResponse> variantValueCreateResponseList = this.productVariantValueService.createOrUpdateAll(variantValues);
            productCreateResponse.setVariantValues(variantValueCreateResponseList);
        }

        this.entityManager.flush();
        this.entityManager.clear(); // <--- Product Variant Index kaydedilirken ProductAttributeValue içerisindeki ProductAttribute'ün sadece id'si geliyor. Çünkü mapper'ında kaydedilirken yalnızca id setleniyor.
                                    //      Onun için entityManager'ı temizledim ve sorgulamada tekrar çekilmesini sağladım.
        
        // Ürüne ait variant_index kaydı oluştur.
        ProductVariantIndexCreateResponse variantIndex = this.productVariantIndexService.initProduct(productCreateResponse);
        productCreateResponse.setVariantIndex(variantIndex);

        this.kafkaProducer.createOpeningProductStock(product);

        logger.info("ID: {} -> Product başarıyla oluşturuldu.", product.getId());

        return productCreateResponse;
    }

    public List<ProductMostInspectedTodayResponse> getMostInspectedTodayProducts() {
        List<MostInspectedProductProjection> result = this.productInspectService.getMostInspectedProductsToday();
        return result
                .stream()
                .map(mostInspectedProduct -> {
                    ProductSearchResponse product = this.findById(mostInspectedProduct.getProductId());

                    List<ProductImageSearchResponse> productImageList = this.productImageService.findByProductId(product.id());
                    String firstImageUrl = !productImageList.isEmpty() ? productImageList.get(0).imageUrl() : null;

                    return new ProductMostInspectedTodayResponse(
                        product.id(),
                        product.name(),
                        product.price(),
                        product.discount(),
                        product.discountedPrice(),
                        firstImageUrl,
                        mostInspectedProduct.getViewCount()
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

package tr.com.huseyinari.ecommerce.product.service;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.client.CategoryClient;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.enums.ProductStatus;
import tr.com.huseyinari.ecommerce.product.exception.ProductAlreadyExistException;
import tr.com.huseyinari.ecommerce.product.exception.ProductNotFoundException;
import tr.com.huseyinari.ecommerce.product.kafka.producer.ProductKafkaProducer;
import tr.com.huseyinari.ecommerce.product.mapper.ProductMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;
import tr.com.huseyinari.ecommerce.product.shared.response.CategorySearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

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

    @Transactional(readOnly = true)
    public ProductSearchResponse findBySkuCode(String skuCode) {
        Product product = this.repository.findBySkuCodeAndStatus(skuCode, ProductStatus.SUCCESS).orElseThrow(ProductNotFoundException::new);
        return ProductMapper.toSearchResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> findAll() {
        return repository.findAllByStatus(ProductStatus.SUCCESS)
                .stream()
                .map(ProductMapper::toSearchResponse)
                .toList();
    }

    @Transactional
    public ProductCreateResponse create(@Valid ProductCreateRequest request) {
        if (repository.findByName(request.name()).isPresent()) {
            throw new ProductAlreadyExistException();
        }

        try {
            SinhaRestApiResponse<CategorySearchResponse> response = categoryClient.findOne(request.categoryId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Geçerli bir kategori seçiniz !");
        } catch (Exception e) {
            throw new RuntimeException("Kategori servisine erişilemedi. Lütfen daha sonra tekrar deneyiniz.");
        }

        Product product = ProductMapper.toEntity(request);
        product.setSkuCode(this.generateSkuCode(product.getName()));
        product.setStatus(ProductStatus.PENDING);

        product = repository.save(product);

        kafkaProducer.createOpeningProductStock(product);

        logger.info("ID: {} -> Product başarıyla oluşturuldu.", product.getId());

        return ProductMapper.toCreateResponse(product);
    }

    private String generateSkuCode(String productName) {
        if (productName == null || productName.equals("")) {
            throw new RuntimeException("Ürün ismi boş olamaz !");
        }
        return productName.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "_");
    }
}

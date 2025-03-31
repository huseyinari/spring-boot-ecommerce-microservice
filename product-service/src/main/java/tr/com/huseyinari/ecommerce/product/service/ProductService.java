package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.product.client.InventoryClient;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductStatus;
import tr.com.huseyinari.ecommerce.product.kafka.producer.ProductKafkaProducer;
import tr.com.huseyinari.ecommerce.product.mapper.ProductMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;
    private final InventoryClient inventoryClient;
    private final ProductKafkaProducer kafkaProducer;

    @Transactional(readOnly = true)
    public ProductSearchResponse findBySkuCode(String skuCode) {
        Optional<Product> optional = this.repository.findBySkuCodeAndStatus(skuCode, ProductStatus.SUCCESS);

        if (optional.isEmpty()) {
            throw new RuntimeException("Ürün bulunamadı !");
        }

        return ProductMapper.toSearchResponse(optional.get());
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> findAll() {
        return repository.findAllByStatus(ProductStatus.SUCCESS)
                .stream()
                .map(ProductMapper::toSearchResponse)
                .toList();
    }

    @Transactional
    public ProductCreateResponse create(ProductCreateRequest request) {
        if (repository.findByName(request.name()).isPresent()) {
            throw new RuntimeException("Aynı isme sahip ürün zaten mevcut.");
        }

        // TODO: categoryId var mı kontrolü ??

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

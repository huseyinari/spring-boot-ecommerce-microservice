package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.product.client.InventoryClient;
import tr.com.huseyinari.ecommerce.product.domain.Product;
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

    @Transactional(readOnly = true)
    public ProductSearchResponse findBySkuCode(String skuCode) {
        Optional<Product> optional = this.repository.findBySkuCode(skuCode);

        if (optional.isEmpty()) {
            throw new RuntimeException("Ürün bulunamadı !");
        }

        return ProductMapper.toSearchResponse(optional.get());
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> findAll() {
        return repository.findAll()
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

        product = repository.save(product);

        inventoryClient.openProductStock(product.getSkuCode());     // TODO: Transactional yönetimi gerekli - işlem başarısız olursa mevcut olmayan ürüne stok açılmış olur

        logger.info("ID: " + product.getId() + " -> Product başarıyla oluşturuldu.");

        return ProductMapper.toCreateResponse(product);
    }

    private String generateSkuCode(String productName) {
        if (productName == null || productName.equals("")) {
            throw new RuntimeException("Ürün ismi boş olamaz !");
        }
        return productName.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "_");
    }
}

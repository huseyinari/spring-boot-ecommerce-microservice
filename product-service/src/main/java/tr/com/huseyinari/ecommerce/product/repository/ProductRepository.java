package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.enums.ProductStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String > {
    Optional<Product> findByName(String name);
    Optional<Product> findBySkuCode(String skuCode);
}

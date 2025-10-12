package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.Optional;

@Repository
public interface ProductRepository extends BaseJpaQueryDslRepository<Product, String > {
    Optional<Product> findByName(String name);
    Optional<Product> findBySkuCode(String skuCode);
}

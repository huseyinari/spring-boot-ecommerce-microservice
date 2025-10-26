package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariant;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends BaseJpaQueryDslRepository<ProductVariant, Long> {
    Optional<ProductVariant> findByQueryName(String queryName);
}

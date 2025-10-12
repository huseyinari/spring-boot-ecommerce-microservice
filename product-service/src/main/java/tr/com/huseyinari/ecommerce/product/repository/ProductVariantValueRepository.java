package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantValue;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

@Repository
public interface ProductVariantValueRepository extends BaseJpaQueryDslRepository<ProductVariantValue, Long> {
}

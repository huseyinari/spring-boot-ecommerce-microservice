package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantIndex;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

@Repository
public interface ProductVariantIndexRepository extends BaseJpaQueryDslRepository<ProductVariantIndex, Long> {
}

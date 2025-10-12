package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantOption;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

@Repository
public interface ProductVariantOptionRepository extends BaseJpaQueryDslRepository<ProductVariantOption, Long> {
}

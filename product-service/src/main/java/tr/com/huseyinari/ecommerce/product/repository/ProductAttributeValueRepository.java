package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttributeValue;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

@Repository
public interface ProductAttributeValueRepository extends BaseJpaQueryDslRepository<ProductAttributeValue, Long> {
}

package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantOption;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;

@Repository
public interface ProductVariantOptionRepository extends BaseJpaQueryDslRepository<ProductVariantOption, Long> {
    List<ProductVariantOption> findByProductVariant_Id(Long id);
}

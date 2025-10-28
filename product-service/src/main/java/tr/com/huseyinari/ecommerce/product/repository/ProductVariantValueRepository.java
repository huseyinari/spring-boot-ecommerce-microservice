package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantValue;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;

@Repository
public interface ProductVariantValueRepository extends BaseJpaQueryDslRepository<ProductVariantValue, Long> {
    @Query(
        "SELECT pvv FROM ProductVariantValue pvv " +
        "INNER JOIN FETCH pvv.productVariant pv " +
        "INNER JOIN FETCH pvv.product p " +
        "WHERE p.id = :productId"
    )
    List<ProductVariantValue> findAllByProductId(@Param("productId") String productId);
}

package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttributeValue;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;

@Repository
public interface ProductAttributeValueRepository extends BaseJpaQueryDslRepository<ProductAttributeValue, Long> {
    @Query(
        "SELECT pav FROM ProductAttributeValue pav " +
        "INNER JOIN FETCH pav.productAttribute " +
        "INNER JOIN FETCH pav.product " +
        "WHERE pav.product.id = :productId"
    )
    List<ProductAttributeValue> findByProduct_Id(@Param("productId") String productId);
}

package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttributeValue;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductAttributeValueRepository extends BaseJpaQueryDslRepository<ProductAttributeValue, Long> {
    @Query(
        "SELECT pav FROM ProductAttributeValue pav " +
        "INNER JOIN FETCH pav.productAttribute pa " +
        "INNER JOIN FETCH pav.product p " +
        "WHERE p.id = :productId " +
        "ORDER BY pa.id ASC"
    )
    List<ProductAttributeValue> findAllByProductIdOrderByProductAttributeId(@Param("productId") String productId);

//    @Query("SELECT pav FROM ProductAttributeValue pav WHERE pav.id IN :idList ORDER BY ID ASC")
    List<ProductAttributeValue> findByIdInOrderById(Collection<Long> idList);
}

package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductInspect;
import tr.com.huseyinari.ecommerce.product.projection.MostInspectedProductProjection;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductInspectRepository extends BaseJpaQueryDslRepository<ProductInspect, Long> {
    @Query("""
        SELECT pi.product.id AS productId,
               COUNT(DISTINCT pi.id) AS viewCount
        FROM ProductInspect pi
        WHERE pi.createdDate >= :startOfDay AND pi.createdDate < :endOfDay
        GROUP BY pi.product.id
        ORDER BY COUNT(DISTINCT pi.ip) DESC
    """)
    List<MostInspectedProductProjection> getMostInspectedProductsToday(@Param("startOfDay") LocalDateTime start, @Param("endOfDay") LocalDateTime end, Pageable pageable);

}

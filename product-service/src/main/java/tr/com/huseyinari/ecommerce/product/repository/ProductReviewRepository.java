package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductReview;
import tr.com.huseyinari.ecommerce.product.projection.MostViewedProductProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    @Query("""
        SELECT pr.product.id AS productId,
               COUNT(DISTINCT pr.id) AS viewCount
        FROM ProductReview pr
        WHERE pr.createdDate >= :startOfDay AND pr.createdDate < :endOfDay
        GROUP BY pr.product.id
        ORDER BY COUNT(DISTINCT pr.ip) DESC
    """)
    List<MostViewedProductProjection> findMostViewedProductsByDate(@Param("startOfDay") LocalDateTime start, @Param("endOfDay") LocalDateTime end, Pageable pageable);

}

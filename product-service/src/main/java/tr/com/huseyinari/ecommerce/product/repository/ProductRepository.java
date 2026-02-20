package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseJpaQueryDslRepository<Product, String > {
    Optional<Product> findByName(String name);
    Optional<Product> findBySkuCode(String skuCode);
    
    @Query("""
        SELECT p FROM Product p
        LEFT JOIN ProductInspect pi ON pi.product.id = p.id
        WHERE p.categoryId = :categoryId AND p.id != :excludeProductId
        GROUP BY p.id
        ORDER BY COUNT(pi.id) DESC
    """)
    List<Product> findByCategoryIdOrderByInspectCount(
        @Param("categoryId") Long categoryId, 
        @Param("excludeProductId") String excludeProductId, 
        Pageable pageable
    );
}

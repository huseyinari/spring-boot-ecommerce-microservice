package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantIndex;
import tr.com.huseyinari.ecommerce.product.projection.ProductVariantIndexGroupsByQueryName;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;

@Repository
public interface ProductVariantIndexRepository extends BaseJpaQueryDslRepository<ProductVariantIndex, Long> {
    void deleteByProductId(String productId);

    @Query(value = """
                /*
                SELECT\s
                pvi.variant_value_index ->> 'cutType' as queryName,\s
                count(pvi.variant_value_index ->> 'cutType') as countQueryName\s
                FROM\s
                product_variant_index pvi\s
                INNER JOIN products p ON pvi.product_id = p.id\s
                GROUP BY pvi.variant_value_index ->> 'cutType'\s
                ORDER BY countQueryName DESC;
                
                --- Yalnızca 1 queryName yerine bir queryName listesine göre gruplamak için aşağıdaki sorguyu (lateral jsonb_each_text) kullandım ---
                */
                
                WITH product_key_value_totals AS (
                    SELECT DISTINCT
                        p.id        AS product_id,
                        e.key       AS queryName,
                        e.value     AS queryValue
                    FROM product_variant_index pvi
                    INNER JOIN products p ON pvi.product_id = p.id
                    CROSS JOIN LATERAL jsonb_each_text(pvi.variant_value_index) e 
                    WHERE
                        e.key IN (:queryNameList)
                        AND (p.category_id IS NULL OR p.category_id = :categoryId)
                )
                
                SELECT
                    queryName,
                    queryValue,
                    COUNT(product_id) AS total
                FROM product_key_value_totals
                GROUP BY queryName, queryValue
                ORDER BY queryName ASC, total DESC
            
            /*
                SELECT
                    e.key   AS queryName,
                    e.value AS queryValue,
                    COUNT(p.id) AS total
                FROM product_variant_index pvi
                INNER JOIN products p ON pvi.product_id = p.id
                CROSS JOIN LATERAL jsonb_each_text(pvi.variant_value_index) e
                WHERE 
                    e.key IN (:queryNameList)
                    AND (p.category_id IS NULL OR p.category_id = :categoryId)        
                GROUP BY e.key, e.value
                ORDER BY queryName ASC, total DESC;
            */
            """, nativeQuery = true)
    List<ProductVariantIndexGroupsByQueryName> findProductVariantIndexGroupsByQueryNameList(
            @Param("queryNameList") List<String> queryNameList,
            @Param("categoryId") Long categoryId
    );
}

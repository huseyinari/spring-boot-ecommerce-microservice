package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttribute;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAttributeRepository extends BaseJpaQueryDslRepository<ProductAttribute, Long> {
    @Query("SELECT pa FROM ProductAttribute pa ORDER BY pa.name ASC")
    List<ProductAttribute> findAllOrderByNameAsc();
    Optional<ProductAttribute> findByQueryName(String queryName);
}

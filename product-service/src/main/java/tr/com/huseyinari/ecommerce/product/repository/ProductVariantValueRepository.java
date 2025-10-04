package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantValue;

@Repository
public interface ProductVariantValueRepository extends JpaRepository<ProductVariantValue, Long> {
}

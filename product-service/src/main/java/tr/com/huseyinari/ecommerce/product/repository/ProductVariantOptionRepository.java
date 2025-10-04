package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantOption;

@Repository
public interface ProductVariantOptionRepository extends JpaRepository<ProductVariantOption, Long> {
}

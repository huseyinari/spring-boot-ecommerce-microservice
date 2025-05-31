package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;

import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByProduct_IdAndStorageObjectId(String productId, Long storageObjectId);
}

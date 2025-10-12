package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends BaseJpaQueryDslRepository<ProductImage, Long> {
    List<ProductImage> findByProduct_IdOrderByCreatedDateAsc(String productId);
    Optional<ProductImage> findByProduct_IdAndStorageObjectId(String productId, Long storageObjectId);
}

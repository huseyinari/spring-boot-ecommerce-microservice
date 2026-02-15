package tr.com.huseyinari.ecommerce.product.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.product.domain.ProductReview;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends BaseJpaQueryDslRepository<ProductReview, Long> {
    List<ProductReview> findByProduct_Id(String id);
}
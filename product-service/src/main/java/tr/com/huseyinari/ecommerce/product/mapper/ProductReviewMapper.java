package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductReview;
import tr.com.huseyinari.ecommerce.product.request.ProductReviewCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductReviewCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductReviewSearchResponse;

@Component
@RequiredArgsConstructor
public class ProductReviewMapper {
    public ProductReviewSearchResponse toSearchResponse(ProductReview productReview, String currentUsername) {
        if (productReview == null) {
            return null;
        }

        return new ProductReviewSearchResponse(
            productReview.getId(),
            productReview.getDescription(),
            productReview.getRating(),
            productReview.getReviewerName(),
            productReview.getReviewerEmail(),
            productReview.getCreatedBy() != null && productReview.getCreatedBy().equals(currentUsername),
            productReview.getCreatedDate()
        );
    }

    public ProductReview toEntity(ProductReviewCreateRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setId(request.productId());

        return ProductReview.builder()
                .product(product)
                .description(request.description())
                .rating(request.rating())
                .reviewerName(request.reviewerName())
                .reviewerEmail(request.reviewerEmail())
                .build();
    }

    public ProductReviewCreateResponse toCreateResponse(ProductReview productReview, String currentUsername) {
        if (productReview == null) {
            return null;
        }

        return new ProductReviewCreateResponse(
            productReview.getId(),
            productReview.getDescription(),
            productReview.getRating(),
            productReview.getReviewerName(),
            productReview.getReviewerEmail(),
            productReview.getCreatedBy() != null && productReview.getCreatedBy().equals(currentUsername)
        );
    }
}

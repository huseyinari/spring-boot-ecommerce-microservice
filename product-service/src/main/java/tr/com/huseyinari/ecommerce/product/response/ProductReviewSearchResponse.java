package tr.com.huseyinari.ecommerce.product.response;

import java.time.LocalDateTime;

public record ProductReviewSearchResponse(
    Long id,
    String description,
    Integer rating,
    String reviewerName,
    String reviewerEmail,
    boolean deletable,   // Yorumu yapan kişi silebileceği için bu kısımda belirtilecek
    LocalDateTime createdDate
) {}

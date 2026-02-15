package tr.com.huseyinari.ecommerce.product.response;

public record ProductReviewCreateResponse(
    Long id,
    String description,
    Integer rating,
    String reviewerName,
    String reviewerEmail,
    boolean deletable   // Yorumu yapan kişi silebileceği için bu kısımda belirtilecek
) {}

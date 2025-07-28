package tr.com.huseyinari.ecommerce.product.response;

public record ProductImageSearchResponse(
    Long id,
    String productId,
    Long storageObjectId,
    String imageUrl
) {}

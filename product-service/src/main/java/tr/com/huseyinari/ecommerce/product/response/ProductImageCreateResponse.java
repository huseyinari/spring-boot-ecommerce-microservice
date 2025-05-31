package tr.com.huseyinari.ecommerce.product.response;

public record ProductImageCreateResponse(
    String productId,
    Long storageObjectId
) {}

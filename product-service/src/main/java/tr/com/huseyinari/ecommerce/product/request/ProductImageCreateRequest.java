package tr.com.huseyinari.ecommerce.product.request;

public record ProductImageCreateRequest(
    String productId,
    Long storageObjectId
) {}

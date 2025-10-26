package tr.com.huseyinari.ecommerce.product.response;

public record ProductVariantValueCreateResponse(
    Long id,
    Long productVariantId,
    String productId,
    String variantValue
) {}

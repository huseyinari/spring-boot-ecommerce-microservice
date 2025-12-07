package tr.com.huseyinari.ecommerce.product.response;

public record ProductVariantValueSearchResponse(
    Long id,
    ProductVariantSearchResponse productVariant,
    String productId,
    String variantValue
) {}
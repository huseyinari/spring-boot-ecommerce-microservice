package tr.com.huseyinari.ecommerce.product.response;

public record ProductVariantOptionCreateResponse(
    Long id,
    Long productVariantId,
    String optionValue
) {}

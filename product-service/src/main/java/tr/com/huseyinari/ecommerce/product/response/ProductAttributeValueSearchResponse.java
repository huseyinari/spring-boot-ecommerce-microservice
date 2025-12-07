package tr.com.huseyinari.ecommerce.product.response;

public record ProductAttributeValueSearchResponse(
    Long id,
    ProductAttributeSearchResponse productAttribute,
    String productId,
    String attributeValue
) {}

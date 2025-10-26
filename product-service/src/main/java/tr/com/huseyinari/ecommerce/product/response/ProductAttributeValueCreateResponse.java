package tr.com.huseyinari.ecommerce.product.response;

public record ProductAttributeValueCreateResponse(
        Long id,
        Long productAttributeId,
        String productId,
        String attributeValue
) {}

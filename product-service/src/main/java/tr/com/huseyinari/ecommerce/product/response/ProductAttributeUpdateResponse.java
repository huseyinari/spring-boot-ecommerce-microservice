package tr.com.huseyinari.ecommerce.product.response;

public record ProductAttributeUpdateResponse(
    Long id,
    String name,
    String queryName,
    String description
) {}

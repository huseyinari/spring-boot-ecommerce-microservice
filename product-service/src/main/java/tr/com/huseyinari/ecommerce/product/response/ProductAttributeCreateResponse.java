package tr.com.huseyinari.ecommerce.product.response;

public record ProductAttributeCreateResponse(
    Long id,
    String name,
    String queryName,
    String description
) {}

package tr.com.huseyinari.ecommerce.product.response;

public record ProductAttributeSearchResponse(
    Long id,
    String name,
    String queryName,
    String description
) {}

package tr.com.huseyinari.ecommerce.product.response;

public record ProductVariantIndexGroupSearchResponse(
    String queryName,
    String queryValue,
    Long total
) {}

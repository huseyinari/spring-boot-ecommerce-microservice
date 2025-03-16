package tr.com.huseyinari.ecommerce.category.response;

public record CategorySearchResponse (
    Long id,
    String name,
    String imageUrl,
    Integer totalProductCount
) {}

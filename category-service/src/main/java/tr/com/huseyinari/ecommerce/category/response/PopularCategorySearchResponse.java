package tr.com.huseyinari.ecommerce.category.response;

public record PopularCategorySearchResponse(
    Long id,
    String name,
    int totalProductCount,
    String imageUrl
) {}

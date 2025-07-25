package tr.com.huseyinari.ecommerce.category.response;

public record PopularCategorySearchResponse(
    String name,
    int totalProductCount,
    Long imageStorageObjectId
) {}

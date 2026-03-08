package tr.com.huseyinari.ecommerce.category.response;

public record CategorySearchResponse (
    Long id,
    String name,
    CategorySearchResponse parent,
    Integer totalProductCount,
    Long storageObjectId,
    String imageUrl
) {}

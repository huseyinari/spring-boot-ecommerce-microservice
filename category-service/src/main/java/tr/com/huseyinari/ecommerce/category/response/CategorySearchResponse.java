package tr.com.huseyinari.ecommerce.category.response;

public record CategorySearchResponse (
    Long id,
    String name,
    Long parentId,
    Integer totalProductCount,
    Long storageObjectId,
    String imageUrl
) {}

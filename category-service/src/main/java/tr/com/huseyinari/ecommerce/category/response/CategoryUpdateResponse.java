package tr.com.huseyinari.ecommerce.category.response;

public record CategoryUpdateResponse(
    Long id,
    String name,
    Long parentId,
    Integer totalProductCount
) {}

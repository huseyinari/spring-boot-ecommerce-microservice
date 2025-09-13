package tr.com.huseyinari.ecommerce.category.response;

public record CategoryCreateResponse(
    Long id,
    String name,
    Long parentId,
    Integer totalProductCount
) {}

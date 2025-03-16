package tr.com.huseyinari.ecommerce.category.response;

public record CategoryCreateResponse(
    Long id,
    String name,
    String imageUrl,
    Integer totalProductCount
) {}

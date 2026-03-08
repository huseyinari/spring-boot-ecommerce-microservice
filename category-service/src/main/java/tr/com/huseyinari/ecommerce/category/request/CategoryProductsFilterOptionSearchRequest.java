package tr.com.huseyinari.ecommerce.category.request;

public record CategoryProductsFilterOptionSearchRequest(
    String name,
    String queryName,
    Long categoryId
) {}

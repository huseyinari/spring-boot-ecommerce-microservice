package tr.com.huseyinari.ecommerce.category.response;

import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterType;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterUiComponent;

public record CategoryProductsFilterOptionSearchResponse(
    String name,
    String queryName,
    CategoryProductsFilterType filterType,
    CategoryProductsFilterUiComponent uiComponent,
    Integer maxFilterOption
) {}

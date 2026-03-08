package tr.com.huseyinari.ecommerce.category.response;

import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterType;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterUiComponent;

public record CategoryProductsFilterOptionUpdateResponse(
    Long id,
    String name,
    String queryName,
    CategoryProductsFilterType filterType,
    CategoryProductsFilterUiComponent uiComponent,
    Integer maxFilterOption,
    CategorySearchResponse category
) {}

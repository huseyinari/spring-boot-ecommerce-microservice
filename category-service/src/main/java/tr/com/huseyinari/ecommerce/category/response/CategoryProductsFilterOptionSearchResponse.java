package tr.com.huseyinari.ecommerce.category.response;

import lombok.Getter;
import lombok.Setter;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterType;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterUiComponent;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryProductsFilterOptionSearchResponse{
    private String name;
    private String queryName;
    private CategoryProductsFilterType filterType;
    private CategoryProductsFilterUiComponent uiComponent;
    private Integer maxFilterOption;
    private List<CategoryProductsFilterOptionSearchResponseValue> values;
}

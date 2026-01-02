package tr.com.huseyinari.ecommerce.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.category.domain.CategoryProductsFilterOption;
import tr.com.huseyinari.ecommerce.category.response.CategoryProductsFilterOptionSearchResponse;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CategoryProductsFilterOptionMapper {
    public CategoryProductsFilterOptionSearchResponse toSearchResponse(CategoryProductsFilterOption categoryProductsFilterOption) {
        if (categoryProductsFilterOption == null) {
            return null;
        }

        CategoryProductsFilterOptionSearchResponse categoryProductsFilterOptionSearchResponse = new CategoryProductsFilterOptionSearchResponse();
        categoryProductsFilterOptionSearchResponse.setName(categoryProductsFilterOption.getName());
        categoryProductsFilterOptionSearchResponse.setQueryName(categoryProductsFilterOption.getQueryName());
        categoryProductsFilterOptionSearchResponse.setFilterType(categoryProductsFilterOption.getFilterType());
        categoryProductsFilterOptionSearchResponse.setUiComponent(categoryProductsFilterOption.getUiComponent());
        categoryProductsFilterOptionSearchResponse.setMaxFilterOption(categoryProductsFilterOption.getMaxFilterOption());
        categoryProductsFilterOptionSearchResponse.setValues(new ArrayList<>());

        return categoryProductsFilterOptionSearchResponse;
    }
}

package tr.com.huseyinari.ecommerce.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.category.domain.CategoryProductsFilterOption;
import tr.com.huseyinari.ecommerce.category.response.CategoryProductsFilterOptionSearchResponse;

@Component
@RequiredArgsConstructor
public class CategoryProductsFilterOptionMapper {
    public CategoryProductsFilterOptionSearchResponse toSearchResponse(CategoryProductsFilterOption categoryProductsFilterOption) {
        if (categoryProductsFilterOption == null) {
            return null;
        }

        return new CategoryProductsFilterOptionSearchResponse(
            categoryProductsFilterOption.getName(),
            categoryProductsFilterOption.getQueryName(),
            categoryProductsFilterOption.getFilterType(),
            categoryProductsFilterOption.getUiComponent(),
            categoryProductsFilterOption.getMaxFilterOption()
        );
    }
}

package tr.com.huseyinari.ecommerce.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.domain.CategoryProductsFilterOption;
import tr.com.huseyinari.ecommerce.category.request.CategoryProductsFilterOptionCreateRequest;
import tr.com.huseyinari.ecommerce.category.request.CategoryProductsFilterOptionUpdateRequest;
import tr.com.huseyinari.ecommerce.category.response.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryProductsFilterOptionMapper {
    private final CategoryMapper categoryMapper;

    public CategoryProductsFilterOptionSearchResponse toSearchResponse(CategoryProductsFilterOption categoryProductsFilterOption) {
        if (categoryProductsFilterOption == null) {
            return null;
        }

        CategoryProductsFilterOptionSearchResponse categoryProductsFilterOptionSearchResponse = new CategoryProductsFilterOptionSearchResponse();
        categoryProductsFilterOptionSearchResponse.setId(categoryProductsFilterOption.getId());
        categoryProductsFilterOptionSearchResponse.setName(categoryProductsFilterOption.getName());
        categoryProductsFilterOptionSearchResponse.setQueryName(categoryProductsFilterOption.getQueryName());
        categoryProductsFilterOptionSearchResponse.setFilterType(categoryProductsFilterOption.getFilterType());
        categoryProductsFilterOptionSearchResponse.setUiComponent(categoryProductsFilterOption.getUiComponent());
        categoryProductsFilterOptionSearchResponse.setMaxFilterOption(categoryProductsFilterOption.getMaxFilterOption());
        categoryProductsFilterOptionSearchResponse.setValues(new ArrayList<>());

        if (categoryProductsFilterOption.getCategory() != null) {
            CategorySearchResponse category = this.categoryMapper.toSearchResponse(categoryProductsFilterOption.getCategory());
            categoryProductsFilterOptionSearchResponse.setCategory(category);
        }

        return categoryProductsFilterOptionSearchResponse;
    }

    public CategoryProductsFilterOptionPageableResponse toSearchPageableResponse(Page<CategoryProductsFilterOption> pageResult) {
        if (pageResult == null) {
            return null;
        }

        List<CategoryProductsFilterOptionSearchResponse> searchResponseList = pageResult
            .getContent()
            .stream()
            .map(this::toSearchResponse)
            .toList();

        CategoryProductsFilterOptionPageableResponse response = new CategoryProductsFilterOptionPageableResponse();
        response.setItems(searchResponseList);
        response.setPage(pageResult.getNumber());
        response.setSize(pageResult.getSize());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setFirst(pageResult.isFirst());
        response.setLast(pageResult.isLast());

        return response;
    }

    public CategoryProductsFilterOption toEntity(CategoryProductsFilterOptionCreateRequest categoryProductsFilterOptionCreateRequest) {
        if (categoryProductsFilterOptionCreateRequest == null) {
            return null;
        }

        Category category = new Category();
        category.setId(categoryProductsFilterOptionCreateRequest.categoryId());

        return CategoryProductsFilterOption.builder()
            .name(categoryProductsFilterOptionCreateRequest.name())
            .queryName(categoryProductsFilterOptionCreateRequest.queryName())
            .filterType(categoryProductsFilterOptionCreateRequest.filterType())
            .uiComponent(categoryProductsFilterOptionCreateRequest.uiComponent())
            .maxFilterOption(categoryProductsFilterOptionCreateRequest.maxFilterOption())
            .category(category)
            .build();
    }

    public CategoryProductsFilterOptionCreateResponse toCreateResponse(CategoryProductsFilterOption categoryProductsFilterOption, CategorySearchResponse category) {
        if (categoryProductsFilterOption == null) {
            return null;
        }

        return new CategoryProductsFilterOptionCreateResponse(
            categoryProductsFilterOption.getId(),
            categoryProductsFilterOption.getName(),
            categoryProductsFilterOption.getQueryName(),
            categoryProductsFilterOption.getFilterType(),
            categoryProductsFilterOption.getUiComponent(),
            categoryProductsFilterOption.getMaxFilterOption(),
            category
        );
    }

    public void fromUpdateRequestToEntity(CategoryProductsFilterOptionUpdateRequest request, CategoryProductsFilterOption categoryProductsFilterOption) {
        if (request == null) {
            return;
        }
        if (categoryProductsFilterOption == null) {
            return;
        }

        Category category = new Category();
        category.setId(request.categoryId());

        categoryProductsFilterOption.setName(request.name());
        categoryProductsFilterOption.setQueryName(request.queryName());
        categoryProductsFilterOption.setFilterType(request.filterType());
        categoryProductsFilterOption.setUiComponent(request.uiComponent());
        categoryProductsFilterOption.setMaxFilterOption(request.maxFilterOption());
        categoryProductsFilterOption.setCategory(category);
    }

    public CategoryProductsFilterOptionUpdateResponse toUpdateResponse(CategoryProductsFilterOption categoryProductsFilterOption, CategorySearchResponse category) {
        if (categoryProductsFilterOption == null) {
            return null;
        }

        return new CategoryProductsFilterOptionUpdateResponse(
            categoryProductsFilterOption.getId(),
            categoryProductsFilterOption.getName(),
            categoryProductsFilterOption.getQueryName(),
            categoryProductsFilterOption.getFilterType(),
            categoryProductsFilterOption.getUiComponent(),
            categoryProductsFilterOption.getMaxFilterOption(),
            category
        );
    }
}

package tr.com.huseyinari.ecommerce.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.category.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.request.CategoryUpdateRequest;
import tr.com.huseyinari.ecommerce.category.response.*;
import tr.com.huseyinari.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final ECommerceConfigurationProperties configurationProperties;

    public Category toEntity(CategoryCreateRequest request) {
        if (request == null) {
            return null;
        }

        return Category.builder()
                .name(request.name())
                .parentId(request.parentId())
                .totalProductCount(0)
                .build();
    }

    public Category toEntity(CategoryUpdateRequest request) {
        if (request == null) {
            return null;
        }

        return Category.builder()
                .id(request.id())
                .name(request.name())
                .parentId(request.parentId())
                .build();
    }

    public CategorySearchResponse toSearchResponse(Category category) {
        if (category == null) {
            return null;
        }

        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        if (StringUtils.isBlank(storageObjectContentUrl)) {
            throw new RuntimeException("Kategori resmi için eksik bilgiler mevcut. Lütfen sistem yöneticiniz ile iletişime geçiniz.");
        }

        return new CategorySearchResponse(
            category.getId(),
            category.getName(),
            category.getParentId(),
            category.getTotalProductCount(),
            category.getImageStorageObjectId(),
            storageObjectContentUrl + "/" + category.getImageStorageObjectId()
        );
    }

    public CategorySearchPageableResponse toSearchPageableResponse(Page<Category> pageResult) {
        if (pageResult == null) {
            return null;
        }

        List<CategorySearchResponse> searchResponseList = pageResult
                .getContent()
                .stream()
                .map(this::toSearchResponse)
                .toList();

        CategorySearchPageableResponse response = new CategorySearchPageableResponse();
        response.setItems(searchResponseList);
        response.setPage(pageResult.getNumber());
        response.setSize(pageResult.getSize());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setFirst(pageResult.isFirst());
        response.setLast(pageResult.isLast());

        return response;
    }

    public CategoryCreateResponse toCreateResponse(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryCreateResponse(category.getId(), category.getName(), category.getParentId(), category.getTotalProductCount());
    }

    public CategoryUpdateResponse toUpdateResponse(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryUpdateResponse(category.getId(), category.getName(), category.getParentId(), category.getTotalProductCount());
    }

    public MenuCategoryResponse toMenuCategoriesResponse(Category category) {
        if (category == null) {
            return null;
        }

        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        if (StringUtils.isBlank(storageObjectContentUrl)) {
            throw new RuntimeException("Menü resmi için eksik bilgiler mevcut. Lütfen sistem yöneticiniz ile iletişime geçiniz.");
        }

        MenuCategoryResponse response = new MenuCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setImageUrl(storageObjectContentUrl + "/" + category.getImageStorageObjectId());
        response.setTotalProductCount(category.getTotalProductCount());
        response.setParentId(category.getParentId());
        response.setSubCategories(new ArrayList<>());

        return response;
    }

    public PopularCategorySearchResponse toPopularCategorySearchResponse(Category category) {
        if (category == null) {
            return null;
        }

        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        if (StringUtils.isBlank(storageObjectContentUrl)) {
            throw new RuntimeException("Kategori resmi için eksik bilgiler mevcut. Lütfen sistem yöneticiniz ile iletişime geçiniz.");
        }

        return new PopularCategorySearchResponse(
            category.getId(),
            category.getName(),
            category.getTotalProductCount(),
            storageObjectContentUrl + "/" + category.getImageStorageObjectId()
        );
    }
}

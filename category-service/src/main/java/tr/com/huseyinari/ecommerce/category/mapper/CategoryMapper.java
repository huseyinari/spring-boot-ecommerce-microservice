package tr.com.huseyinari.ecommerce.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.category.repository.JpaEntityResolver;
import tr.com.huseyinari.ecommerce.category.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.request.CategoryUpdateRequest;
import tr.com.huseyinari.ecommerce.category.response.*;
import tr.com.huseyinari.utils.NumberUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final ECommerceConfigurationProperties configurationProperties;
    private final JpaEntityResolver jpaEntityResolver;

    public Category toEntity(CategoryCreateRequest request) {
        if (request == null) {
            return null;
        }

        Category parent = null;
        if (NumberUtils.greaterZero(request.parentId())) {
            parent = this.jpaEntityResolver.getReference(Category.class, request.parentId());
        }

        return Category.builder()
                .name(request.name())
                .parent(parent)
                .totalProductCount(0)
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

        CategorySearchResponse parent = null;
        if (category.getParent() != null) {
            parent = this.toSearchResponse(category.getParent());
        }

        return new CategorySearchResponse(
            category.getId(),
            category.getName(),
            parent,
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

        Long parentId = category.getParent() != null ? category.getParent().getId() : null;

        return new CategoryCreateResponse(category.getId(), category.getName(), parentId, category.getTotalProductCount());
    }

    public void fromUpdateRequestToEntity(CategoryUpdateRequest request, Category category) {
        if (request == null) {
            return;
        }
        if (category == null) {
            return;
        }

        Category parent = null;
        if (NumberUtils.greaterZero(request.parentId())) {
            parent = this.jpaEntityResolver.getReference(Category.class, request.parentId());
        }

        category.setName(request.name());
        category.setParent(parent);
    }

    public CategoryUpdateResponse toUpdateResponse(Category category) {
        if (category == null) {
            return null;
        }

        Long parentId = category.getParent() != null ? category.getParent().getId() : null;

        return new CategoryUpdateResponse(category.getId(), category.getName(), parentId, category.getTotalProductCount());
    }

    public MenuCategoryResponse toMenuCategoriesResponse(Category category) {
        if (category == null) {
            return null;
        }

        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        if (StringUtils.isBlank(storageObjectContentUrl)) {
            throw new RuntimeException("Menü resmi için eksik bilgiler mevcut. Lütfen sistem yöneticiniz ile iletişime geçiniz.");
        }

        Long parentId = category.getParent() != null ? category.getParent().getId() : null;

        MenuCategoryResponse response = new MenuCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setImageUrl(storageObjectContentUrl + "/" + category.getImageStorageObjectId());
        response.setTotalProductCount(category.getTotalProductCount());
        response.setParentId(parentId);
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

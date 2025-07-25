package tr.com.huseyinari.ecommerce.category.mapper;

import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.response.CategoryCreateResponse;
import tr.com.huseyinari.ecommerce.category.response.CategorySearchResponse;
import tr.com.huseyinari.ecommerce.category.response.MenuCategoryResponse;
import tr.com.huseyinari.ecommerce.category.response.PopularCategorySearchResponse;

import java.util.ArrayList;

public class CategoryMapper {
    private CategoryMapper() {

    }

    public static Category toEntity(CategoryCreateRequest request) {
        return Category.builder()
                .name(request.name())
                .imageUrl("/images/category.jpg")   // <----- TODO: Örnek olarak yazıldı. Storage servis ayarlandıgı zaman resim kaydedilmeli ve url bilgisi tanımlanmalı
                .totalProductCount(0)
                .build();
    }

    public static CategorySearchResponse toSearchResponse(Category category) {
        return new CategorySearchResponse(category.getId(), category.getName(), category.getImageUrl(), category.getTotalProductCount());
    }

    public static CategoryCreateResponse toCreateResponse(Category category) {
        return new CategoryCreateResponse(category.getId(), category.getName(), category.getImageUrl(), category.getTotalProductCount());
    }

    public static MenuCategoryResponse toMenuCategoriesResponse(Category category) {
        MenuCategoryResponse response = new MenuCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setImageUrl(category.getImageUrl());
        response.setTotalProductCount(category.getTotalProductCount());
        response.setParentId(category.getParentId());
        response.setSubCategories(new ArrayList<>());

        return response;
    }

    public static PopularCategorySearchResponse toPopularCategorySearchResponse(Category category) {
        return new PopularCategorySearchResponse(category.getName(), category.getTotalProductCount(), category.getImageStorageObjectId());
    }
}

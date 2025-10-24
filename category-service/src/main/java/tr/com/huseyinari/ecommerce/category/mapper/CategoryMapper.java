package tr.com.huseyinari.ecommerce.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.category.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.response.CategoryCreateResponse;
import tr.com.huseyinari.ecommerce.category.response.CategorySearchResponse;
import tr.com.huseyinari.ecommerce.category.response.MenuCategoryResponse;
import tr.com.huseyinari.ecommerce.category.response.PopularCategorySearchResponse;
import tr.com.huseyinari.utils.StringUtils;

import java.util.ArrayList;

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
                .imageStorageObjectId(Long.valueOf(-1))     // TODO: Kategori eklenirken resim seçilemeyecek diye düşündüm. Eğer bu imkan sağlanırsa düzenlenmeli
                .totalProductCount(0)
                .build();
    }

    public CategorySearchResponse toSearchResponse(Category category) {
        if (category == null) {
            return null;
        }

        return new CategorySearchResponse(category.getId(), category.getName(), category.getParentId(), category.getTotalProductCount());
    }

    public CategoryCreateResponse toCreateResponse(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryCreateResponse(category.getId(), category.getName(), category.getParentId(), category.getTotalProductCount());
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
            category.getName(),
            category.getTotalProductCount(),
            storageObjectContentUrl + "/" + category.getImageStorageObjectId()
        );
    }
}

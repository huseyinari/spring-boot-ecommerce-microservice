package tr.com.huseyinari.ecommerce.category.mapper;

import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.response.CategoryCreateResponse;
import tr.com.huseyinari.ecommerce.category.response.CategorySearchResponse;

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
}

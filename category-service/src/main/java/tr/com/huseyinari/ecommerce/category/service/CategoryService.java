package tr.com.huseyinari.ecommerce.category.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.category.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.exception.CategoryNotFoundException;
import tr.com.huseyinari.ecommerce.category.mapper.CategoryMapper;
import tr.com.huseyinari.ecommerce.category.repository.CategoryRepository;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.response.CategoryCreateResponse;
import tr.com.huseyinari.ecommerce.category.response.CategorySearchResponse;
import tr.com.huseyinari.ecommerce.category.response.MenuCategoryResponse;
import tr.com.huseyinari.ecommerce.category.response.PopularCategorySearchResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;
    private final ECommerceConfigurationProperties configurationProperties;

    public List<CategorySearchResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(CategoryMapper::toSearchResponse)
                .toList();
    }

    public CategorySearchResponse findOne(Long id) {
        Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        return CategoryMapper.toSearchResponse(category);
    }

    public CategoryCreateResponse create(CategoryCreateRequest request) {
        Category category = CategoryMapper.toEntity(request);
        category = this.repository.save(category);

        return CategoryMapper.toCreateResponse(category);
    }

    public List<MenuCategoryResponse> getMenuCategories() {
        List<MenuCategoryResponse> allCategories =
                this.repository.findAll()
                        .stream()
                        .map(CategoryMapper::toMenuCategoriesResponse)
                        .toList();

        List<MenuCategoryResponse> menuCategoryResponseList = allCategories.stream().filter(item -> item.getParentId() == null).toList(); // root kategorileri ekle

        for (MenuCategoryResponse rootCategory : menuCategoryResponseList) {
            this.setSubCategories(allCategories, rootCategory);
        }

        return menuCategoryResponseList;
    }

    // Recursive bir şekilde tüm kategorilere alt kategorilerini setler.
    private void setSubCategories(List<MenuCategoryResponse> allCategories, MenuCategoryResponse selectedCategory) {
        List<MenuCategoryResponse> subCategories = allCategories.stream().filter(item -> selectedCategory.getId().equals(item.getParentId())).toList();

        if (subCategories.isEmpty())
            return;

        selectedCategory.setSubCategories(subCategories);

        for (MenuCategoryResponse subCategory : subCategories) {
            this.setSubCategories(allCategories, subCategory);
        }
    }

    public List<PopularCategorySearchResponse> getPopularCategories() {
        Pageable pageable = PageRequest.of(0, 6);
        List<Category> popularCategories = this.repository.findByParentIdOrderByTotalProductCountDesc(null, pageable);

        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        return popularCategories
                .stream()
                .map(item -> CategoryMapper.toPopularCategorySearchResponse(item, storageObjectContentUrl))
                .toList();
    }
}

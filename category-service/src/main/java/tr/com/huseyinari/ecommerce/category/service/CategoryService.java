package tr.com.huseyinari.ecommerce.category.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategorySearchResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    public CategorySearchResponse findOne(Long id) {
        Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        return this.mapper.toSearchResponse(category);
    }

    public CategoryCreateResponse create(CategoryCreateRequest request) {
        Category category = this.mapper.toEntity(request);
        category = this.repository.save(category);

        return this.mapper.toCreateResponse(category);
    }

    public List<MenuCategoryResponse> getMenuCategories() {
        List<MenuCategoryResponse> allCategories =
                this.repository.findAll()
                        .stream()
                        .map(this.mapper::toMenuCategoriesResponse)
                        .toList();

        List<MenuCategoryResponse> menuCategoryResponseList = allCategories
                .stream()
                .filter(category -> category.getParentId() == null)
                .toList(); // root kategorileri ekle

        for (MenuCategoryResponse rootCategory : menuCategoryResponseList) {
            this.setSubCategories(allCategories, rootCategory);
        }

        return menuCategoryResponseList;
    }

    // Recursive bir şekilde tüm kategorilere alt kategorilerini setler.
    private void setSubCategories(List<MenuCategoryResponse> allCategories, MenuCategoryResponse selectedCategory) {
        List<MenuCategoryResponse> subCategories = allCategories
                .stream()
                .filter(category -> selectedCategory.getId().equals(category.getParentId()))
                .toList();

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

        return popularCategories
                .stream()
                .map(this.mapper::toPopularCategorySearchResponse)
                .toList();
    }
}

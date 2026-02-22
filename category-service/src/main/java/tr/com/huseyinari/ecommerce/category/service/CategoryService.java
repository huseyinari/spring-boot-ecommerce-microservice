package tr.com.huseyinari.ecommerce.category.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.domain.QCategory;
import tr.com.huseyinari.ecommerce.category.exception.CategoryNotFoundException;
import tr.com.huseyinari.ecommerce.category.mapper.CategoryMapper;
import tr.com.huseyinari.ecommerce.category.repository.CategoryRepository;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.response.*;
import tr.com.huseyinari.utils.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public CategorySearchPageableResponse search(String search, Pageable pageable) {
        QCategory qCategory = QCategory.category;

        BooleanBuilder where = new BooleanBuilder();

        if (StringUtils.isNotBlank(search)) {
            where.and(qCategory.name.likeIgnoreCase("%" + search + "%"));
        }

        JPAQueryFactory totalQuery = new JPAQueryFactory(this.entityManager);
        Long total = totalQuery
                .select(qCategory.count())
                .from(qCategory)
                .where(where)
                .fetchOne();

        JPAQueryFactory query = new JPAQueryFactory(this.entityManager);
        List<Category> categoryList = query
            .select(qCategory)
            .from(qCategory)
            .where(where)
            .orderBy(qCategory.id.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Page<Category> pageResult = new PageImpl<>(categoryList, pageable, total != null ? total : 0L);
        return this.mapper.toSearchPageableResponse(pageResult);
    }

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

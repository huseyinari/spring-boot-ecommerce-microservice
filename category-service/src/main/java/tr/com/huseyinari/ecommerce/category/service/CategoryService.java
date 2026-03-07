package tr.com.huseyinari.ecommerce.category.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import feign.FeignException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.category.client.StorageClient;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.domain.QCategory;
import tr.com.huseyinari.ecommerce.category.exception.CategoryNotFoundException;
import tr.com.huseyinari.ecommerce.category.mapper.CategoryMapper;
import tr.com.huseyinari.ecommerce.category.repository.CategoryRepository;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.request.CategoryUpdateRequest;
import tr.com.huseyinari.ecommerce.category.response.*;
import tr.com.huseyinari.ecommerce.category.shared.response.UploadCategoryImageResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;
import tr.com.huseyinari.utils.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final StorageClient storageClient;

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

    @Transactional
    public CategoryCreateResponse create(@Valid CategoryCreateRequest request) {
        if (request.image() == null || request.image().isEmpty()) {
            throw new RuntimeException("Kategori resmi zorunludur!");
        }

        final Long imageStorageObjectId;
        try {
            SinhaRestApiResponse<UploadCategoryImageResponse> uploadResponse = storageClient.uploadCategoryImage(request.image());
            imageStorageObjectId = uploadResponse.getData().storageObjectId();
        } catch (FeignException.BadRequest e) {
            throw new RuntimeException("Geçersiz dosya formatı!");
        } catch (Exception e) {
            throw new RuntimeException("Dosya yüklenirken hata oluştu: " + e.getMessage());
        }

        Category category = this.mapper.toEntity(request);
        category.setImageStorageObjectId(imageStorageObjectId);
        category.setTotalProductCount(0);
        category = this.repository.save(category); // TODO: Veritabanı işlemi hata verirse resim boşu boşuna upload edilmiş olur.

        logger.info("Kategori başarıyla oluşturuldu. ID: {}", category.getId());
        return this.mapper.toCreateResponse(category);
    }

    @Transactional
    public CategoryUpdateResponse update(@Valid CategoryUpdateRequest request) {
        CategorySearchResponse existCategory = this.findOne(request.id());

        Category category = this.mapper.toEntity(request);
        category.setImageStorageObjectId(existCategory.storageObjectId());
        category.setTotalProductCount(existCategory.totalProductCount());  // Toplam ürün sayısını değiştirme

        if (request.image() != null && !request.image().isEmpty()) {    // Resim güncellenecek demektir.
            try {
                SinhaRestApiResponse<UploadCategoryImageResponse> uploadResponse = storageClient.uploadCategoryImage(request.image());
                Long newImageStorageObjectId = uploadResponse.getData().storageObjectId();

                category.setImageStorageObjectId(newImageStorageObjectId);
            } catch (FeignException.BadRequest e) {
                throw new RuntimeException("Geçersiz dosya formatı!");
            } catch (Exception e) {
                throw new RuntimeException("Dosya yüklenirken hata oluştu: " + e.getMessage());
            }
        }

        category = this.repository.save(category);

        // TODO: Veritabanı işlemi hata verirse resim boşu boşuna siliniyor ve veri kaybı oluşuyor.
        if (!existCategory.storageObjectId().equals(category.getImageStorageObjectId())) {
            this.storageClient.deleteFile(existCategory.storageObjectId());  // resim değiştiyse eskisini sil
        }

        logger.info("Kategori başarıyla güncellendi. ID: {}", category.getId());
        return this.mapper.toUpdateResponse(category);
    }

    @Transactional
    public void delete(Long id) {
        CategorySearchResponse category = this.findOne(id);

        if (category.totalProductCount() > 0) {
            throw new RuntimeException("Bu kategoriye ait ürün olduğu için silinemez!");
        }

        try {
            this.storageClient.deleteFile(category.storageObjectId());
        } catch (FeignException.NotFound e) {
            logger.info("Silinmek istenen kategori resmi bulunamadı. ID: {}", category.storageObjectId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Kategori resmi silinirken bir hata oluştu. ID: {}", category.storageObjectId());
            throw new RuntimeException("Kategori resmi silinirken bir hata oluştu.");
        }

        this.repository.deleteById(id);

        logger.info("Kategori başarıyla silindi. ID: {}", id);
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

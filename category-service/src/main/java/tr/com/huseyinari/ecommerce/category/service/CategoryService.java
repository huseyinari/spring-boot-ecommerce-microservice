package tr.com.huseyinari.ecommerce.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.ecommerce.category.mapper.CategoryMapper;
import tr.com.huseyinari.ecommerce.category.repository.CategoryRepository;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.response.CategoryCreateResponse;
import tr.com.huseyinari.ecommerce.category.response.CategorySearchResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public List<CategorySearchResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(CategoryMapper::toSearchResponse)
                .toList();
    }

    public CategorySearchResponse findOne(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new RuntimeException("Kategori bulunamadı !"));
        return CategoryMapper.toSearchResponse(category);
    }

    public CategoryCreateResponse create(CategoryCreateRequest request) {
        Category category = CategoryMapper.toEntity(request);
        category = this.repository.save(category);

        return CategoryMapper.toCreateResponse(category);
    }
}

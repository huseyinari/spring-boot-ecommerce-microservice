package tr.com.huseyinari.ecommerce.category.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.category.mapper.CategoryProductsFilterOptionMapper;
import tr.com.huseyinari.ecommerce.category.repository.CategoryProductsFilterOptionRepository;
import tr.com.huseyinari.ecommerce.category.response.CategoryProductsFilterOptionSearchResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryProductsFilterOptionService {
    private final Logger logger = LoggerFactory.getLogger(CategoryProductsFilterOptionService.class);

    private final CategoryProductsFilterOptionRepository repository;
    private final CategoryProductsFilterOptionMapper mapper;

    public List<CategoryProductsFilterOptionSearchResponse> getFilterOptionsByCategoryId(Long categoryId) {
        return this.repository.findByCategory_Id(categoryId)
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }
}

package tr.com.huseyinari.ecommerce.category.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.category.domain.CategoryProductsFilterOption;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;

@Repository
public interface CategoryProductsFilterOptionRepository extends BaseJpaQueryDslRepository<CategoryProductsFilterOption, Long> {
    List<CategoryProductsFilterOption> findByCategory_Id(Long id);
}

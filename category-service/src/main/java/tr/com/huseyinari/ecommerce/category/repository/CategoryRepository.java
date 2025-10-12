package tr.com.huseyinari.ecommerce.category.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.category.domain.Category;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseJpaQueryDslRepository<Category, Long> {
    List<Category> findByParentIdOrderByTotalProductCountDesc(@Nullable Long parentId, Pageable pageable);
}

package tr.com.huseyinari.ecommerce.category.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.category.domain.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIdOrderByTotalProductCountDesc(@Nullable Long parentId, Pageable pageable);
}

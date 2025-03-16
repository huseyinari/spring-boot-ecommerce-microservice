package tr.com.huseyinari.ecommerce.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.category.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

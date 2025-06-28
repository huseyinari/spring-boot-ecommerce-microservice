package tr.com.huseyinari.ecommerce.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.category.domain.CarouselItem;

import java.util.List;

@Repository
public interface CarouselItemRepository extends JpaRepository<CarouselItem, Long> {
    List<CarouselItem> findByCarouselName(String carouselName);
}

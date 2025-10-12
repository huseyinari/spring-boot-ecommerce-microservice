package tr.com.huseyinari.ecommerce.category.repository;

import org.springframework.stereotype.Repository;
import tr.com.huseyinari.ecommerce.category.domain.CarouselItem;
import tr.com.huseyinari.springdatajpa.repository.BaseJpaQueryDslRepository;

import java.util.List;

@Repository
public interface CarouselItemRepository extends BaseJpaQueryDslRepository<CarouselItem, Long> {
    List<CarouselItem> findByCarouselName(String carouselName);
}

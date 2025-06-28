package tr.com.huseyinari.ecommerce.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.category.domain.CarouselItem;
import tr.com.huseyinari.ecommerce.category.mapper.CarouselItemMapper;
import tr.com.huseyinari.ecommerce.category.repository.CarouselItemRepository;
import tr.com.huseyinari.ecommerce.category.response.CarouselItemSearchResponse;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarouselItemService {
    private final CarouselItemRepository repository;

    @Transactional(readOnly = true)
    public List<CarouselItemSearchResponse> findByCarouselName(String carouselName) {
        return this.repository.findByCarouselName(carouselName)
                .stream()
                .sorted(Comparator.comparingInt(CarouselItem::getListOrder))
                .map(CarouselItemMapper::toSearchResponse)
                .toList();
    }
}

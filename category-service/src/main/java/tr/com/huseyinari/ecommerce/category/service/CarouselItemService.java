package tr.com.huseyinari.ecommerce.category.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CarouselItemService.class);

    private final CarouselItemRepository repository;
    private final CarouselItemMapper mapper;

    @Transactional(readOnly = true)
    public List<CarouselItemSearchResponse> findByCarouselName(String carouselName) {
        return this.repository.findByCarouselName(carouselName)
                .stream()
                .sorted(Comparator.comparingInt(CarouselItem::getListOrder))
                .map(this.mapper::toSearchResponse)
                .toList();
    }
}

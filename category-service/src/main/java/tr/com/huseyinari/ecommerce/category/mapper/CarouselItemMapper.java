package tr.com.huseyinari.ecommerce.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.category.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.category.domain.CarouselItem;
import tr.com.huseyinari.ecommerce.category.response.CarouselItemSearchResponse;
import tr.com.huseyinari.utils.StringUtils;

@Component
@RequiredArgsConstructor
public class CarouselItemMapper {
    private final ECommerceConfigurationProperties configurationProperties;

    public CarouselItemSearchResponse toSearchResponse(CarouselItem carouselItem) {
        if (carouselItem == null) {
            return null;
        }

        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        if (StringUtils.isBlank(storageObjectContentUrl)) {
            throw new RuntimeException("Carousel resmi için eksik bilgiler mevcut. Lütfen sistem yöneticiniz ile iletişime geçiniz.");
        }

        return new CarouselItemSearchResponse(
            carouselItem.getTitle(),
            carouselItem.getSubtitle(),
            carouselItem.getLink(),
            carouselItem.getLinkTitle(),
            carouselItem.getListOrder(),
            storageObjectContentUrl + "/" + carouselItem.getImageStorageObjectId()
        );
    }
}

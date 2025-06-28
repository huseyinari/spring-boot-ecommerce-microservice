package tr.com.huseyinari.ecommerce.category.mapper;

import tr.com.huseyinari.ecommerce.category.domain.CarouselItem;
import tr.com.huseyinari.ecommerce.category.response.CarouselItemSearchResponse;

public class CarouselItemMapper {
    private CarouselItemMapper() {

    }

    public static CarouselItemSearchResponse toSearchResponse(CarouselItem carouselItem) {
        return new CarouselItemSearchResponse(
            carouselItem.getTitle(),
            carouselItem.getSubtitle(),
            carouselItem.getLink(),
            carouselItem.getLinkTitle(),
            carouselItem.getListOrder(),
            carouselItem.getImageStorageObjectId()
        );
    }
}

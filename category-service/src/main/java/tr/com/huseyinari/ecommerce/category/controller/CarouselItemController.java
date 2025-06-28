package tr.com.huseyinari.ecommerce.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.huseyinari.ecommerce.category.response.CarouselItemSearchResponse;
import tr.com.huseyinari.ecommerce.category.service.CarouselItemService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carousel-item")
@RequiredArgsConstructor
public class CarouselItemController {
    private final CarouselItemService service;

    @GetMapping("/{carouselName}")
    public ResponseEntity<List<CarouselItemSearchResponse>> findByCarouselName(@PathVariable String carouselName) {
        List<CarouselItemSearchResponse> response = this.service.findByCarouselName(carouselName);
        return ResponseEntity.ok(response);
    }
}

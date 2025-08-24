package tr.com.huseyinari.ecommerce.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
@Tag(name = "Carousel Item Controller", description = "Carousel Elemanları Yönetimi")
public class CarouselItemController {
    private final CarouselItemService service;

    @Operation(
        summary = "Carousel elemanlarının bilgilerini getir.",
        description = "Gönderilen carousel ismine sahip Carousel içerisinde gösterilecek içeriklere ait bilgileri getirir."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Carousel element'leri getirildi.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = CarouselItemSearchResponse.class))
        )
    )
    @GetMapping("/{carouselName}")
    public ResponseEntity<List<CarouselItemSearchResponse>> findByCarouselName(@PathVariable String carouselName) {
        List<CarouselItemSearchResponse> response = this.service.findByCarouselName(carouselName);
        return ResponseEntity.ok(response);
    }
}

package tr.com.huseyinari.ecommerce.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantIndexCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantIndexCreateResponse;
import tr.com.huseyinari.ecommerce.product.service.ProductVariantIndexService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-variant-index")
@RequiredArgsConstructor
@Tag(name = "Product Variant Index Controller", description = "Ürün Varyant Kombinasyonu Yönetimi")
public class ProductVariantIndexController {
    private final ProductVariantIndexService service;

    @Operation(
        summary = "Ürün Varyant Kombinasyonları oluştur",
        description = "Yeni ürün varyant kombinasyonları oluşturur.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ürün varyant kombinasyonları başarıyla oluşturuldu",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = ProductVariantIndexCreateResponse.class))
            )
        )
    })
    @PostMapping
    public ResponseEntity<List<ProductVariantIndexCreateResponse>> create(@RequestBody List<ProductVariantIndexCreateRequest> requestList) {
        List<ProductVariantIndexCreateResponse> response = this.service.createAll(requestList);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeUpdateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeSearchPageableResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeSearchResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeUpdateResponse;
import tr.com.huseyinari.ecommerce.product.service.ProductAttributeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-attribute")
@RequiredArgsConstructor
@Tag(name = "Product Attribute Controller", description = "Ürün Özelliği Yönetimi")
public class ProductAttributeController {
    private final ProductAttributeService service;

    @Operation(
        summary = "Ürün özelliği ara",
        description = "Belirli koşullara göre ürün özelliklerini sayfalandırılmış şekilde getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
        responseCode = "200",
        description = "Koşullara göre ürün özellikleri getirildi",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = ProductAttributeSearchPageableResponse.class))
        )
    )
    @GetMapping("/search")
    public ResponseEntity<ProductAttributeSearchPageableResponse> search(@RequestParam String search, @ParameterObject Pageable pageable) {
        ProductAttributeSearchPageableResponse response = this.service.search(search, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Tüm ürün özelliklerini getir",
        description = "Tüm ürün özelliklerini getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Tüm ürün özellikleri getirildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = ProductAttributeSearchResponse.class))
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<ProductAttributeSearchResponse>> findAll() {
        List<ProductAttributeSearchResponse> response = this.service.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Id'ye göre ürün özelliği ara",
        description = "Belirtilen Id ile eşleşen ürün özelliğini getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ürün özelliği bulundu",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductAttributeSearchResponse.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductAttributeSearchResponse> findOne(@PathVariable Long id) {
        ProductAttributeSearchResponse response = this.service.findOne(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Ürün özelliği ekle",
        description = "Yeni bir ürün özelliği kaydeder.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Yeni ürün özelliği başarıyla kaydedildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductAttributeCreateResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ProductAttributeCreateResponse> create(@RequestBody ProductAttributeCreateRequest request) {
        ProductAttributeCreateResponse response = this.service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Ürün özelliği güncelle",
        description = "Mevcut bir ürün özelliğini günceller.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ürün özelliği başarıyla güncellendi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductAttributeUpdateResponse.class)
            )
        )
    })
    @PutMapping
    public ResponseEntity<ProductAttributeUpdateResponse> update(@RequestBody ProductAttributeUpdateRequest request) {
        ProductAttributeUpdateResponse response = this.service.update(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Ürün özelliği sil",
        description = "Mevcut bir ürün özelliğini siler.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Ürün özelliği başarıyla silindi."
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

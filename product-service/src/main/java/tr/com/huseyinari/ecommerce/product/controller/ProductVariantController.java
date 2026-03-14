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
import tr.com.huseyinari.ecommerce.product.request.ProductVariantCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantUpdateRequest;
import tr.com.huseyinari.ecommerce.product.response.*;
import tr.com.huseyinari.ecommerce.product.service.ProductVariantService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-variant")
@RequiredArgsConstructor
@Tag(name = "Product Variant Controller", description = "Ürün Varyantı Yönetimi")
public class ProductVariantController {
    private final ProductVariantService service;

    @Operation(
        summary = "Ürün varyantı ara",
        description = "Belirli koşullara göre ürün varyantlarını sayfalandırılmış şekilde getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
        responseCode = "200",
        description = "Koşullara göre ürün varyantları getirildi",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ProductVariantSearchPageableResponse.class)
        )
    )
    @GetMapping("/search")
    public ResponseEntity<ProductVariantSearchPageableResponse> search(@RequestParam(required = false) String search, @ParameterObject Pageable pageable) {
        ProductVariantSearchPageableResponse response = this.service.search(search, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Tüm ürün varyantlarını getir",
        description = "Tüm ürün varyantlarını getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Tüm ürün varyantları getirildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = ProductVariantSearchResponse.class))
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<ProductVariantSearchResponse>> findAll() {
        List<ProductVariantSearchResponse> response = this.service.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Ürün varyantı ekle",
        description = "Yeni bir ürün varyantı kaydeder.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Yeni ürün varyantı başarıyla kaydedildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductVariantCreateResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ProductVariantCreateResponse> create(@RequestBody ProductVariantCreateRequest request) {
        ProductVariantCreateResponse response = this.service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Ürün varyantı güncelle",
        description = "Mevcut bir ürün varyantını günceller.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ürün varyantı başarıyla güncellendi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductVariantUpdateResponse.class)
            )
        )
    })
    @PutMapping
    public ResponseEntity<ProductVariantUpdateResponse> update(@RequestBody ProductVariantUpdateRequest request) {
        ProductVariantUpdateResponse response = this.service.update(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Ürün varyantı sil",
        description = "Mevcut bir ürün varyantını siler.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Ürün varyantı başarıyla silindi."
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

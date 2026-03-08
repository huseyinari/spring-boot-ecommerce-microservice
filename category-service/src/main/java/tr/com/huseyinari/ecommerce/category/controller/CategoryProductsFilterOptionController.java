package tr.com.huseyinari.ecommerce.category.controller;

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
import tr.com.huseyinari.ecommerce.category.request.CategoryProductsFilterOptionCreateRequest;
import tr.com.huseyinari.ecommerce.category.request.CategoryProductsFilterOptionSearchRequest;
import tr.com.huseyinari.ecommerce.category.request.CategoryProductsFilterOptionUpdateRequest;
import tr.com.huseyinari.ecommerce.category.response.*;
import tr.com.huseyinari.ecommerce.category.service.CategoryProductsFilterOptionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category/products-filter-option")
@RequiredArgsConstructor
@Tag(name = "Category Products Filter Option Controller", description = "Kategori Ürün Filtreleme Yönetimi")
public class CategoryProductsFilterOptionController {
    private final CategoryProductsFilterOptionService service;

    @Operation(
        summary = "Kategori ürün filtreleme seçeneği ara",
        description = "Belirli koşullara göre kategori ürün filtreleme seçeneğini sayfalandırılmış şekilde getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
        responseCode = "200",
        description = "Koşullara göre kategori ürün filtreleme seçeneği getirildi",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = CategoryProductsFilterOptionPageableResponse.class))
        )
    )
    @PostMapping("/search")
    public ResponseEntity<CategoryProductsFilterOptionPageableResponse> search(@RequestBody CategoryProductsFilterOptionSearchRequest request, @ParameterObject Pageable pageable) {
        CategoryProductsFilterOptionPageableResponse response = this.service.search(request, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Id'ye göre kategori ürün filtreleme seçeneği ara",
        description = "Belirtilen Id ile eşleşen kategori ürün filtreleme seçeneğini getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Kategori ürün filtreleme seçeneği bulundu",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CategoryProductsFilterOptionSearchResponse.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryProductsFilterOptionSearchResponse> findOne(@PathVariable Long id) {
        CategoryProductsFilterOptionSearchResponse response = this.service.findOne(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Kategoriye ait ürün filtreleme alanlarını getir.",
        description = "Kategoriye ait ürünlerin listelendiği sayfada, ürünlerin hangi kriterlere göre aranabileceğini belirten verileri döner."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Kategoriye ait ürün filtreleme seçenekleri getirildi.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = CategoryProductsFilterOptionSearchResponse.class))
        )
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CategoryProductsFilterOptionSearchResponse>> getFilterOptionsByCategoryId(@PathVariable Long categoryId) {
        List<CategoryProductsFilterOptionSearchResponse> response = this.service.getFilterOptionsByCategoryId(categoryId);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Kategori ürün filtreleme seçeneği oluştur",
        description = "Yeni bir kategori filtreleme seçeneği oluşturur.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Kategori ürün filtreleme seçeneği başarıyla oluşturuldu.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CategoryProductsFilterOptionCreateResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<CategoryProductsFilterOptionCreateResponse> create(@RequestBody CategoryProductsFilterOptionCreateRequest request) {
        CategoryProductsFilterOptionCreateResponse response = this.service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Kategori ürün filtreleme seçeneği güncelle",
        description = "Mevcut bir kategori ürün filtreleme seçeneğini günceller.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Kategori ürün filtreleme seçeneği başarıyla güncellendi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CategoryProductsFilterOptionUpdateResponse.class)
            )
        )
    })
    @PutMapping
    public ResponseEntity<CategoryProductsFilterOptionUpdateResponse> update(@RequestBody CategoryProductsFilterOptionUpdateRequest request) {
        CategoryProductsFilterOptionUpdateResponse response = this.service.update(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Kategori ürün filtreleme seçeneği sil",
        description = "Mevcut bir kategori ürün filtreleme seçeneğini siler.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Kategori ürün filtreleme seçeneği başarıyla silindi."
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

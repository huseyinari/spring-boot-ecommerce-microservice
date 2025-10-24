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
import tr.com.huseyinari.ecommerce.category.response.CategoryProductsFilterOptionSearchResponse;
import tr.com.huseyinari.ecommerce.category.service.CategoryProductsFilterOptionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category/products-filter-option")
@RequiredArgsConstructor
@Tag(name = "Category Products Filter Option Controller", description = "Kategori Ürün Filtreleme Yönetimi")
public class CategoryProductsFilterOptionController {
    private final CategoryProductsFilterOptionService service;

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
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<CategoryProductsFilterOptionSearchResponse>> getFilterOptionsByCategoryId(@PathVariable Long categoryId) {
        List<CategoryProductsFilterOptionSearchResponse> response = this.service.getFilterOptionsByCategoryId(categoryId);
        return ResponseEntity.ok(response);
    }
}

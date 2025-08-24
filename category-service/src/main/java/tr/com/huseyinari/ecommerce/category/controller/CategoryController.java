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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.response.CategoryCreateResponse;
import tr.com.huseyinari.ecommerce.category.response.CategorySearchResponse;
import tr.com.huseyinari.ecommerce.category.response.MenuCategoryResponse;
import tr.com.huseyinari.ecommerce.category.response.PopularCategorySearchResponse;
import tr.com.huseyinari.ecommerce.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category Controller", description = "Kategori Yönetimi")
public class CategoryController {
    private final CategoryService service;

    @Operation(
        summary = "Tüm kategorileri getir.",
        description = "Herhangi bir koşul olmaksızın tüm kategorileri getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
        responseCode = "200",
        description = "Tüm kategoriler getirildi.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = CategorySearchResponse.class))
        )
    )
    @GetMapping
    public ResponseEntity<List<CategorySearchResponse>> findAll() {
        List<CategorySearchResponse> response = this.service.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Id'ye göre kategori ara",
        description = "Belirtilen Id ile eşleşen kategoriyi getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Kategori bulundu",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CategorySearchResponse.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategorySearchResponse> findOne(@PathVariable Long id) {
        CategorySearchResponse response = this.service.findOne(id);
        return ResponseEntity.ok(response);
    }

//    @Operation(
//        summary = "Kategori oluştur",
//        description = "Yeni bir kategori kaydı oluşturur.",
//        security = @SecurityRequirement(name = "bearerAuth")
//    )
//    @ApiResponses({
//        @ApiResponse(
//            responseCode = "201",
//            description = "Kategori başarıyla oluşturuldu",
//            content = @Content(
//                mediaType = MediaType.APPLICATION_JSON_VALUE,
//                schema = @Schema(implementation = CategoryCreateResponse.class)
//            )
//        )
//    })
//    @PostMapping
//    public ResponseEntity<CategoryCreateResponse> create(@RequestBody CategoryCreateRequest request) {
//        CategoryCreateResponse response = this.service.create(request);
//        return new ResponseEntity<>(response , HttpStatus.CREATED);
//    }

    @Operation(
        summary = "Menü için kategori listesini getir.",
        description = "Anasayfada gösterilen menüdeki kategori bilgilerini getirir."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Kategoriler başarıyla getirildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = MenuCategoryResponse.class))
            )
        )
    })
    @GetMapping("/menu")
    public ResponseEntity<List<MenuCategoryResponse>> getMenuCategories() {
        List<MenuCategoryResponse> response = this.service.getMenuCategories();
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Popüler kategori listesini getir.",
        description = "En fazla ürünü bulunan kategorileri sırasıyla getirir."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Kategoriler başarıyla getirildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = PopularCategorySearchResponse.class))
            )
        )
    })
    @GetMapping("/popular")
    public ResponseEntity<List<PopularCategorySearchResponse>> getPopularCategories() {
        List<PopularCategorySearchResponse> response = this.service.getPopularCategories();
        return ResponseEntity.ok(response);
    }
}

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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductSearchParameters;
import tr.com.huseyinari.ecommerce.product.response.ProductCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductMostViewedTodayResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchPageableResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;
import tr.com.huseyinari.ecommerce.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Ürün Yönetimi")
public class ProductController {
    private final ProductService service;

    @Operation(
        summary = "Ürün ara",
        description = "Ürünleri kriterlere göre arar ve sayfalama yaparak bilgileri döner.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Arama kriterlerine göre ürünler bulundu.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductSearchPageableResponse.class)
            )
        )
    })
    @GetMapping("/search")
    public ResponseEntity<ProductSearchPageableResponse> search(ProductSearchParameters params, Pageable pageable) {
        ProductSearchPageableResponse response = this.service.search(params, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
        summary = "Tüm ürünleri getir",
        description = "Tüm ürünleri getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Tüm ürünler getirildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = ProductSearchResponse.class))
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<ProductSearchResponse>> findAll() {
        List<ProductSearchResponse> response = this.service.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Stok koduna göre ürün ara",
        description = "Belirtilen stok koduyla eşleşen ürünü getir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ürün bulundu",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductSearchResponse.class)
            )
        )
    })
    @GetMapping("/{skuCode}")
    public ResponseEntity<ProductSearchResponse> findOne(@PathVariable String skuCode) {
        ProductSearchResponse response = this.service.findBySkuCode(skuCode);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Ürün oluştur",
        description = "Yeni bir ürün kaydı oluşturur.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ürün başarıyla oluşturuldu",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductCreateResponse.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Geçersiz istek", content = @Content),
        @ApiResponse(responseCode = "401", description = "Authentication hatası", content = @Content),
        @ApiResponse(responseCode = "500", description = "Sunucu hatası", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest request) {
        ProductCreateResponse response = this.service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Bugünün en çok incelenen ürünlerini getir",
        description = "Mevcut gün içerisinde en çok incelenen 4 ürünü getirir."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "En çok incelenen ürünler başarıyla getirildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = ProductMostViewedTodayResponse.class))
            )
        )
    })
    @GetMapping("/most-viewed/today")
    public ResponseEntity<List<ProductMostViewedTodayResponse>> mostViewed() {
        List<ProductMostViewedTodayResponse> responseList = this.service.getMostViewedTodayProducts();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}

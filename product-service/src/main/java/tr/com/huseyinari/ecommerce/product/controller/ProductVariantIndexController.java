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
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantIndexCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantIndexCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantIndexGroupSearchResponse;
import tr.com.huseyinari.ecommerce.product.service.ProductVariantIndexService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-variant-index")
@RequiredArgsConstructor
@Tag(name = "Product Variant Index Controller", description = "Ürün Varyant Kombinasyonu Yönetimi")
public class ProductVariantIndexController {
    private final ProductVariantIndexService service;

    @Operation(
        summary = "Ürün Varyant Kombinasyonlarını Sorgu Adına Göre Gruplar",
        description = "Gönderilen sorgu adı listesini içerek ürün varyant kombinasyonlarını gruplayarak, değerlerini ve total ürün sayısını döndürür.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Ürün varyant kombinasyonları gruplandı ve listelendi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = ProductVariantIndexGroupSearchResponse.class))
            )
        )
    })
    @GetMapping("/group")
    public List<ProductVariantIndexGroupSearchResponse> findProductVariantIndexGroupsByQueryNameList(@RequestParam List<String> queryNameList) {
        return this.service.findProductVariantIndexGroupsByQueryNameList(queryNameList);
    }

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

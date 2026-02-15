package tr.com.huseyinari.ecommerce.product.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import tr.com.huseyinari.ecommerce.product.request.ProductReviewCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductReviewCreateResponse;
import tr.com.huseyinari.ecommerce.product.service.ProductReviewService;

@RestController
@RequestMapping("/api/v1/product-review")
@RequiredArgsConstructor
@Tag(name = "Product Review Controller", description = "Ürün Değerlendirmeleri Yönetimi")
public class ProductReviewController {
    private final ProductReviewService service;

    @Operation(
        summary = "Ürün değerlendirmesi oluştur",
        description = "Kullanıcının bir ürün hakkında değerlendirmelerini paylaşmasını sağlar.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ürün değerlendirmesi oluşturuldu.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductReviewCreateResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ProductReviewCreateResponse> create(ProductReviewCreateRequest request) {
        ProductReviewCreateResponse response = this.service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Ürün değerlendirmesini sil",
        description = "Kullanıcının bir ürün hakkında yaptığı değerlendirmeyi silmesini sağlar.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Ürün değerlendirmesi silindi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductReviewCreateResponse.class)
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.huseyinari.ecommerce.product.request.ProductImageCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductImageCreateResponse;
import tr.com.huseyinari.ecommerce.product.service.ProductImageService;

@RestController
@RequestMapping("/api/v1/product-image")
@RequiredArgsConstructor
@Tag(name = "Product Image Controller", description = "Ürün Resimleri Yönetimi")
public class ProductImageController {
    private final ProductImageService service;

    @Operation(
        summary = "Ürün resmi oluşturur.",
        description = "Bir ürün ve resmi birbiriyle ilişkilendirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ürün resmi oluşturuldu.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductImageCreateResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ProductImageCreateResponse> create(@RequestBody ProductImageCreateRequest request) {
        ProductImageCreateResponse response = this.service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

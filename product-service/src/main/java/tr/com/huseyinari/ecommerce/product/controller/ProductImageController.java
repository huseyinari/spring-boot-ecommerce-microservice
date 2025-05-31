package tr.com.huseyinari.ecommerce.product.controller;

import lombok.RequiredArgsConstructor;
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
public class ProductImageController {
    private final ProductImageService service;

    @PostMapping
    public ResponseEntity<ProductImageCreateResponse> create(@RequestBody ProductImageCreateRequest request) {
        ProductImageCreateResponse response = service.create(request);
        return ResponseEntity.ok(response);
    }
}

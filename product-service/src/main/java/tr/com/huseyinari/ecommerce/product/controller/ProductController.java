package tr.com.huseyinari.ecommerce.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;
import tr.com.huseyinari.ecommerce.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/{skuCode}")
    public ResponseEntity<ProductSearchResponse> findOne(@PathVariable String skuCode) {
        ProductSearchResponse response = this.service.findBySkuCode(skuCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductSearchResponse>> findAll() {
        List<ProductSearchResponse> responseList = service.findAll();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest request) {
        ProductCreateResponse response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

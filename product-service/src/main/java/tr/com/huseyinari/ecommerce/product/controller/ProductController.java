package tr.com.huseyinari.ecommerce.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
public class ProductController {
    private final ProductService service;

    @GetMapping("/search")
    public ResponseEntity<ProductSearchPageableResponse> search(ProductSearchParameters params, Pageable pageable) {
        ProductSearchPageableResponse response = this.service.search(params, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductSearchResponse>> findAll() {
        List<ProductSearchResponse> response = this.service.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<ProductSearchResponse> findOne(@PathVariable String skuCode) {
        ProductSearchResponse response = this.service.findBySkuCode(skuCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest request) {
        ProductCreateResponse response = this.service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/most-viewed/today")
    public ResponseEntity<List<ProductMostViewedTodayResponse>> mostViewed() {
        List<ProductMostViewedTodayResponse> responseList = this.service.getMostViewedTodayProducts();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}

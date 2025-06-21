package tr.com.huseyinari.ecommerce.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinari.ecommerce.category.request.CategoryCreateRequest;
import tr.com.huseyinari.ecommerce.category.response.CategoryCreateResponse;
import tr.com.huseyinari.ecommerce.category.response.CategorySearchResponse;
import tr.com.huseyinari.ecommerce.category.response.MenuCategoryResponse;
import tr.com.huseyinari.ecommerce.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategorySearchResponse>> findAll() {
        List<CategorySearchResponse> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorySearchResponse> findOne(@PathVariable Long id) {
        CategorySearchResponse response = service.findOne(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoryCreateResponse> create(@RequestBody CategoryCreateRequest request) {
        CategoryCreateResponse response = service.create(request);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MenuCategoryResponse>> getMenuCategories() {
        List<MenuCategoryResponse> response = service.getMenuCategories();
        return ResponseEntity.ok(response);
    }
}

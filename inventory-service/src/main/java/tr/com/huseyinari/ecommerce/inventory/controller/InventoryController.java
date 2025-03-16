package tr.com.huseyinari.ecommerce.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinari.ecommerce.inventory.request.StockIncreaseRequest;
import tr.com.huseyinari.ecommerce.inventory.response.StockIncreaseResponse;
import tr.com.huseyinari.ecommerce.inventory.service.InventoryService;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;

    @PostMapping("{skuCode}")
    public ResponseEntity<Void> openProductStock(@PathVariable String skuCode) {
        this.service.openProductStock(skuCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/is-in-stock")
    public ResponseEntity<Boolean> isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        boolean result = this.service.inInStock(skuCode, quantity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/increase")
    public ResponseEntity<StockIncreaseResponse> increaseStock(@RequestBody StockIncreaseRequest request) {
        StockIncreaseResponse response = this.service.increaseStock(request);
        return ResponseEntity.ok(response);
    }
}

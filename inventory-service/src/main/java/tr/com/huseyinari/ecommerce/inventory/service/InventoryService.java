package tr.com.huseyinari.ecommerce.inventory.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.inventory.domain.Inventory;
import tr.com.huseyinari.ecommerce.inventory.exception.ProductSkuCodeNotFoundException;
import tr.com.huseyinari.ecommerce.inventory.mapper.InventoryMapper;
import tr.com.huseyinari.ecommerce.inventory.repository.InventoryRepository;
import tr.com.huseyinari.ecommerce.inventory.request.CreateOpeningProductStockRequest;
import tr.com.huseyinari.ecommerce.inventory.request.StockIncreaseRequest;
import tr.com.huseyinari.ecommerce.inventory.response.CreateOpeningProductStockResponse;
import tr.com.huseyinari.ecommerce.inventory.response.StockIncreaseResponse;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Validated
public class InventoryService {
    private final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    @Transactional(readOnly = true)
    public boolean inInStock(String skuCode, Integer quantity) {
        return this.repository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    @Transactional
    public StockIncreaseResponse increaseStock(StockIncreaseRequest request) {
        Inventory currentStock = this.repository.findBySkuCode(request.getSkuCode()).orElseThrow(ProductSkuCodeNotFoundException::new);

        Integer lastQuantity = currentStock.getQuantity() + request.getQuantity();
        currentStock.setQuantity(lastQuantity);

        this.repository.save(currentStock);

        return this.mapper.toIncreaseResponse(currentStock);
    }

    @Transactional
    public CreateOpeningProductStockResponse  createOpeningProductStock(@Valid CreateOpeningProductStockRequest request) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(request.skuCode());
        inventory.setQuantity(0);

        inventory.setCreatedBy(request.createdBy());
        inventory.setCreatedDate(LocalDateTime.now());

        inventory = this.repository.save(inventory);

        return this.mapper.toCreateOpeningProductStockResponse(inventory);
    }
}

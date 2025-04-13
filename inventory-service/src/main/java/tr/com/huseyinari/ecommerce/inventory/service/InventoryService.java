package tr.com.huseyinari.ecommerce.inventory.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.inventory.domain.Inventory;
import tr.com.huseyinari.ecommerce.inventory.exception.ProductSkuCodeNotFoundException;
import tr.com.huseyinari.ecommerce.inventory.mapper.InventoryMapper;
import tr.com.huseyinari.ecommerce.inventory.repository.InventoryRepository;
import tr.com.huseyinari.ecommerce.inventory.request.StockIncreaseRequest;
import tr.com.huseyinari.ecommerce.inventory.response.StockIncreaseResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository repository;

    @Transactional(readOnly = true)
    public boolean inInStock(String skuCode, Integer quantity) {
        return this.repository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    // KAFKA İLE AÇILIŞ STOĞU OLUŞTURULDUGU İÇİN SERVİS KALDIRILDI
//    @Transactional
//    public void openProductStock(String skuCode) {
//        Inventory inventory = new Inventory();
//        inventory.setSkuCode(skuCode);      // Bu servis, yeni ürün oluşturulduğunda bizim tarafımızdan çağrılacağı için skuCode'u kontrol ettirmedim.
//        inventory.setQuantity(0);
//
//        this.repository.save(inventory);
//    }

    @Transactional
    public StockIncreaseResponse increaseStock(StockIncreaseRequest request) {
        Inventory currentStock = this.repository.findBySkuCode(request.getSkuCode()).orElseThrow(ProductSkuCodeNotFoundException::new);

        Integer lastQuantity = currentStock.getQuantity() + request.getQuantity();
        currentStock.setQuantity(lastQuantity);

        this.repository.save(currentStock);

        return InventoryMapper.toIncreaseResponse(currentStock);
    }
}

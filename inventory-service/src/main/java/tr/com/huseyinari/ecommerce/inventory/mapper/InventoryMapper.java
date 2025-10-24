package tr.com.huseyinari.ecommerce.inventory.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.inventory.domain.Inventory;
import tr.com.huseyinari.ecommerce.inventory.response.CreateOpeningProductStockResponse;
import tr.com.huseyinari.ecommerce.inventory.response.StockIncreaseResponse;

@Component
@RequiredArgsConstructor
public class InventoryMapper {
    public StockIncreaseResponse toIncreaseResponse(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        return new StockIncreaseResponse(inventory.getSkuCode(), inventory.getQuantity());
    }

    public CreateOpeningProductStockResponse toCreateOpeningProductStockResponse(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        return new CreateOpeningProductStockResponse(inventory.getId(), inventory.getSkuCode(), inventory.getQuantity());
    }
}

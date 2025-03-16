package tr.com.huseyinari.ecommerce.inventory.mapper;

import tr.com.huseyinari.ecommerce.inventory.domain.Inventory;
import tr.com.huseyinari.ecommerce.inventory.response.StockIncreaseResponse;

public class InventoryMapper {
    private InventoryMapper() {

    }

    public static StockIncreaseResponse toIncreaseResponse(Inventory inventory) {
        return new StockIncreaseResponse(inventory.getSkuCode(), inventory.getQuantity());
    }
}

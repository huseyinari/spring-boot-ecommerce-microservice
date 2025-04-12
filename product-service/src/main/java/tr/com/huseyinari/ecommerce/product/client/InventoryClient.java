package tr.com.huseyinari.ecommerce.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "inventory-service", path = "/api/v1/inventory" /* url = "localhost:8080" */)
public interface InventoryClient {
    @PostMapping("/{skuCode}")
    void openProductStock(@PathVariable String skuCode);
}

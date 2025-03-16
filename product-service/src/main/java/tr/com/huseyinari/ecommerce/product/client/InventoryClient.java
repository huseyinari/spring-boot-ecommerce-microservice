package tr.com.huseyinari.ecommerce.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "inventory", url = "${huseyinari.ecommerce.metadata.inventory-service-url}")
public interface InventoryClient {
    @PostMapping("/api/v1/inventory/{skuCode}")
    void openProductStock(@PathVariable String skuCode);
}

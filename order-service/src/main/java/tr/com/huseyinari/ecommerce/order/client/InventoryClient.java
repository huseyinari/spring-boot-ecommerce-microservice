package tr.com.huseyinari.ecommerce.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@FeignClient(value = "inventory", url = "${huseyinari.ecommerce.metadata.inventory-service-url}")
public interface InventoryClient {
    @GetMapping("/api/v1/inventory/is-in-stock")
    SinhaRestApiResponse<Boolean> isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}

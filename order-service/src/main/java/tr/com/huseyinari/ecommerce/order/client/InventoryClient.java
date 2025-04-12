package tr.com.huseyinari.ecommerce.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@FeignClient(value = "inventory-service", path = "/api/v1/inventory" /* url = "localhost:8080" */)
public interface InventoryClient {
    @GetMapping("/is-in-stock")
    SinhaRestApiResponse<Boolean> isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}

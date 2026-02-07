package tr.com.huseyinari.ecommerce.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tr.com.huseyinari.ecommerce.order.shared.response.ProductSearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@FeignClient(value = "product-service", path = "/api/v1/product" /* url = "localhost:8080" */)
public interface ProductClient {
    @GetMapping("/sku/{skuCode}")
    SinhaRestApiResponse<ProductSearchResponse> get(@PathVariable String skuCode);
}

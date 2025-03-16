package tr.com.huseyinari.ecommerce.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tr.com.huseyinari.ecommerce.order.shared.response.ProductSearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@FeignClient(value = "product", url = "${huseyinari.ecommerce.metadata.product-service-url}")
public interface ProductClient {
    @GetMapping("/api/v1/product/{skuCode}")
    SinhaRestApiResponse<ProductSearchResponse> get(@PathVariable String skuCode);
}

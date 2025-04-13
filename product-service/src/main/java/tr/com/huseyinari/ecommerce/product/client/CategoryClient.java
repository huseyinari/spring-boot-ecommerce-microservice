package tr.com.huseyinari.ecommerce.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tr.com.huseyinari.ecommerce.product.shared.response.CategorySearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@FeignClient(value = "category-service", path = "api/v1/category" /*, url = "localhost:8080" */)
public interface CategoryClient {
    @GetMapping("/{id}")
    SinhaRestApiResponse<CategorySearchResponse> findOne(@PathVariable Long id);
}

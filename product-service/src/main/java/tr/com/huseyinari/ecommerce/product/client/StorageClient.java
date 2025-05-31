package tr.com.huseyinari.ecommerce.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tr.com.huseyinari.ecommerce.product.shared.response.StorageObjectSearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@FeignClient(value = "storage-service", path = "/api/v1/storage" /* url = "localhost:8080" */)
public interface StorageClient {
    @GetMapping("/{id}")
    SinhaRestApiResponse<StorageObjectSearchResponse> findOne(@PathVariable Long id);
}

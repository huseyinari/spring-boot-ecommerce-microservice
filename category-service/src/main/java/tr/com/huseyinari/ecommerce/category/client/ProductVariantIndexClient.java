package tr.com.huseyinari.ecommerce.category.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.huseyinari.ecommerce.category.shared.response.ProductVariantIndexGroupSearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

import java.util.List;

@FeignClient(value = "product-service", path = "/api/v1/product-variant-index")
public interface ProductVariantIndexClient {

    @GetMapping("/group")
    SinhaRestApiResponse<List<ProductVariantIndexGroupSearchResponse>> findProductVariantIndexGroupsByQueryNameList(
        @RequestParam List<String> queryNameList,
        @RequestParam(required = false) Long categoryId
    );

}

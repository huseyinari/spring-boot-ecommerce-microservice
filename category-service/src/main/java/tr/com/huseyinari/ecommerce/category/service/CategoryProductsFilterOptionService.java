package tr.com.huseyinari.ecommerce.category.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.category.client.ProductVariantIndexClient;
import tr.com.huseyinari.ecommerce.category.mapper.CategoryProductsFilterOptionMapper;
import tr.com.huseyinari.ecommerce.category.repository.CategoryProductsFilterOptionRepository;
import tr.com.huseyinari.ecommerce.category.response.CategoryProductsFilterOptionSearchResponse;
import tr.com.huseyinari.ecommerce.category.response.CategoryProductsFilterOptionSearchResponseValue;
import tr.com.huseyinari.ecommerce.category.shared.ProductVariantIndexGroupSearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryProductsFilterOptionService {
    private final Logger logger = LoggerFactory.getLogger(CategoryProductsFilterOptionService.class);

    private final CategoryProductsFilterOptionRepository repository;
    private final CategoryProductsFilterOptionMapper mapper;
    private final ProductVariantIndexClient productVariantIndexClient;

    @Transactional(readOnly = true)
    public List<CategoryProductsFilterOptionSearchResponse> getFilterOptionsByCategoryId(Long categoryId) {
        List<CategoryProductsFilterOptionSearchResponse> categoryProductsFilterOptionSearchResponseList = this.repository.findByCategory_Id(categoryId)
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();

        List<String> queryNameList = categoryProductsFilterOptionSearchResponseList
                .stream()
                .map(CategoryProductsFilterOptionSearchResponse::getQueryName)
                .toList();

        SinhaRestApiResponse<List<ProductVariantIndexGroupSearchResponse>> productVariantIndexApiResponse;
        try {
            productVariantIndexApiResponse = this.productVariantIndexClient.findProductVariantIndexGroupsByQueryNameList(queryNameList);
        } catch (FeignException.NotFound exception) {
            throw new RuntimeException("Ürün varyant kombinasyonları servisi bulunamadı.");
        } catch (Exception exception) {
            throw new RuntimeException("Ürün varyant kombinasyonları servisine erişilemedi. Lütfen daha sonra tekrar deneyiniz.");
        }

        List<ProductVariantIndexGroupSearchResponse> productVariantIndexGroupSearchResponseList = productVariantIndexApiResponse.getData();

        for (CategoryProductsFilterOptionSearchResponse categoryProductsFilterOptionSearchResponse : categoryProductsFilterOptionSearchResponseList) {
            List<CategoryProductsFilterOptionSearchResponseValue> values = productVariantIndexGroupSearchResponseList
                    .stream()
                    .filter(productVariantIndexGroupSearchResponse ->
                        productVariantIndexGroupSearchResponse.getQueryName().equals(categoryProductsFilterOptionSearchResponse.getQueryName())
                    )
                    .map(productVariantIndexGroupSearchResponse ->
                        new CategoryProductsFilterOptionSearchResponseValue(productVariantIndexGroupSearchResponse.getQueryValue(), productVariantIndexGroupSearchResponse.getTotal())
                    )
                    .toList();

            categoryProductsFilterOptionSearchResponse.getValues().addAll(values);
        }

        return categoryProductsFilterOptionSearchResponseList;
    }
}

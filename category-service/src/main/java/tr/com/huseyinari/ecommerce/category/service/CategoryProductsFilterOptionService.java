package tr.com.huseyinari.ecommerce.category.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.category.client.ProductVariantIndexClient;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterType;
import tr.com.huseyinari.ecommerce.category.mapper.CategoryProductsFilterOptionMapper;
import tr.com.huseyinari.ecommerce.category.repository.CategoryProductsFilterOptionRepository;
import tr.com.huseyinari.ecommerce.category.response.CategoryProductsFilterOptionSearchResponse;
import tr.com.huseyinari.ecommerce.category.response.CategoryProductsFilterOptionSearchResponseValue;
import tr.com.huseyinari.ecommerce.category.shared.response.ProductVariantIndexGroupSearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;
import tr.com.huseyinari.utils.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryProductsFilterOptionService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryProductsFilterOptionService.class);

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

        if (CollectionUtils.isEmpty(queryNameList))
            return Collections.emptyList();

        SinhaRestApiResponse<List<ProductVariantIndexGroupSearchResponse>> productVariantIndexApiResponse;
        try {
            productVariantIndexApiResponse = this.productVariantIndexClient.findProductVariantIndexGroupsByQueryNameList(queryNameList, categoryId);
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
                        new CategoryProductsFilterOptionSearchResponseValue(
                            productVariantIndexGroupSearchResponse.getQueryValue(),
                            BigDecimal.ZERO,
                            BigDecimal.ZERO,
                            productVariantIndexGroupSearchResponse.getTotal()
                        )
                    )
                    .toList();

            Integer maxOptions = categoryProductsFilterOptionSearchResponse.getMaxFilterOption();

            if (categoryProductsFilterOptionSearchResponse.getMaxFilterOption() > 0) {
                values = values.stream()    // maxFilterOption değerinden fazla sonuç varsa en fazla ürün içerenden başlayarak x tane al
                        .sorted((a, b) -> Long.compare(b.getTotal(), a.getTotal()))
                        .limit(maxOptions)
                        .toList();
            }

            if (CategoryProductsFilterType.RANGE.equals(categoryProductsFilterOptionSearchResponse.getFilterType())) {
                List<CategoryProductsFilterOptionSearchResponseValue> rangeValues = buildRangeValues(values, maxOptions);

                categoryProductsFilterOptionSearchResponse.getValues().addAll(rangeValues);
            } else {
                categoryProductsFilterOptionSearchResponse.getValues().addAll(values);
            }
        }

        return categoryProductsFilterOptionSearchResponseList;
    }

    private List<CategoryProductsFilterOptionSearchResponseValue> buildRangeValues(
            List<CategoryProductsFilterOptionSearchResponseValue> values,
            int rangeCount
    ) {
        if (values == null || values.isEmpty() || rangeCount <= 0) {
            return List.of();
        }

        // 1️⃣ Fiyatları queryValue'dan al
        List<BigDecimal> prices = values.stream()
                .map(v -> new BigDecimal(v.getQueryValue()))
                .toList();

        BigDecimal minPrice = prices.stream().min(BigDecimal::compareTo).get();
        BigDecimal maxPrice = prices.stream().max(BigDecimal::compareTo).get();

        long minRaw = minPrice.longValue();
        long maxRaw = maxPrice.longValue();

        // 2️⃣ Step hesapla
        long rawStep = (maxRaw - minRaw) / rangeCount;
        if (rawStep == 0) rawStep = 1;

        long step = normalizeStep(rawStep);

        long min = (minRaw / step) * step;
        long max = ((maxRaw + step - 1) / step) * step;

        List<CategoryProductsFilterOptionSearchResponseValue> ranges = new ArrayList<>();

        // 3️⃣ Range üret
        for (int i = 0; i < rangeCount; i++) {
            long rangeMin = min + step * i;
            long rangeMax = (i == rangeCount - 1)
                    ? max
                    : rangeMin + step;

            CategoryProductsFilterOptionSearchResponseValue r =
                    new CategoryProductsFilterOptionSearchResponseValue();

            r.setQueryValue(null);
            r.setMinValue(BigDecimal.valueOf(rangeMin));
            r.setMaxValue(BigDecimal.valueOf(rangeMax));
            r.setTotal(0L);

            ranges.add(r);
        }

        // 4️⃣ Ürünleri range’lere dağıt
        for (CategoryProductsFilterOptionSearchResponseValue v : values) {
            long price = new BigDecimal(v.getQueryValue()).longValue();

            int index = (int) ((price - min) / step);
            if (index < 0) index = 0;
            if (index >= rangeCount) index = rangeCount - 1;

            ranges.get(index).setTotal(
                    ranges.get(index).getTotal() + v.getTotal()
            );
        }

        return ranges;
    }


    private long normalizeStep(long rawStep) {
        long magnitude = (long) Math.pow(10, String.valueOf(rawStep).length() - 1);

        if (rawStep <= magnitude) return magnitude;
        if (rawStep <= magnitude * 2) return magnitude * 2;
        if (rawStep <= magnitude * 5) return magnitude * 5;

        return magnitude * 10;
    }

}

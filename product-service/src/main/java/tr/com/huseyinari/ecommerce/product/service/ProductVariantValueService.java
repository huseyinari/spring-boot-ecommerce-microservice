package tr.com.huseyinari.ecommerce.product.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantValue;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantValueMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantValueRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantValueCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantValueCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantValueSearchResponse;
import tr.com.huseyinari.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantValueService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantValueService.class);

    private final ProductVariantValueRepository repository;
    private final ProductVariantValueMapper mapper;

    @Transactional(readOnly = true)
    public List<ProductVariantValueSearchResponse> findAllByProductIdOrderByProductVariantId(String productId) {
        return this.repository.findAllByProductIdOrderByProductVariantId(productId)
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    @Transactional
    public List<ProductVariantValueCreateResponse> createOrUpdateAll(List<ProductVariantValueCreateRequest> requestList) {
        if (CollectionUtils.isEmpty(requestList)) {
            return Collections.emptyList();
        }

        boolean allProductIdsAreSame = requestList.stream()
                .map(ProductVariantValueCreateRequest::productId)
                .distinct()
                .count() == 1;

        if (!allProductIdsAreSame) {
            throw new IllegalArgumentException("Tek seferde farklı ürünlere ait varyant değerleri güncellenemez !");
        }

        final String productId = requestList.get(0).productId();
        final List<ProductVariantValue> existList = this.repository.findAllByProductIdOrderByProductVariantId(productId);

        // yeni listede olmayan varyantları sil
        for (ProductVariantValue exist : existList) {
            boolean hasRequest = requestList
                    .stream()
                    .anyMatch(request -> exist.getProductVariant().getId().compareTo(request.productVariantId()) == 0);

            if (!hasRequest) {
                this.repository.delete(exist);
            }
        }

        final List<ProductVariantValue> result = new ArrayList<>();

        // Olmayanları ekle, olanları güncelle
        for (ProductVariantValueCreateRequest request : requestList) {
            ProductVariantValue exist = existList.stream()
                    .filter(variantValue -> variantValue.getProductVariant().getId().equals(request.productVariantId()))
                    .findFirst()
                    .orElse(null);

            if (exist != null) {
                exist.setVariantValue(request.variantValue());
                exist = this.repository.save(exist);

                result.add(exist);
            } else {
                ProductVariantValue productVariantValue = this.mapper.toEntity(request);
                productVariantValue = this.repository.save(productVariantValue);

                result.add(productVariantValue);
            }
        }

        return result
                .stream()
                .map(this.mapper::toCreateResponse)
                .toList();
    }
}

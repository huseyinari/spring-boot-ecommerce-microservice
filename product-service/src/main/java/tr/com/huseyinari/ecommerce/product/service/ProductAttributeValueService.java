package tr.com.huseyinari.ecommerce.product.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttributeValue;
import tr.com.huseyinari.ecommerce.product.mapper.ProductAttributeValueMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductAttributeValueRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeValueCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeValueCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeValueSearchResponse;
import tr.com.huseyinari.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ProductAttributeValueService {
    private final Logger logger = LoggerFactory.getLogger(ProductAttributeValueService.class);

    private final ProductAttributeValueRepository repository;
    private final ProductAttributeValueMapper mapper;

    @Transactional(readOnly = true)
    public List<ProductAttributeValueSearchResponse> findByIdInOrderById(List<Long> idList) {
        List<ProductAttributeValue> productAttributeValue = this.repository.findByIdInOrderById(idList);
        return productAttributeValue
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductAttributeValueSearchResponse> findAllByProductIdOrderByProductAttributeId(@NotBlank(message = "Ürün belirtilmedi") String productId) {
        return this.repository.findAllByProductIdOrderByProductAttributeId(productId)
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    @Transactional
    public List<ProductAttributeValueCreateResponse> createOrUpdateAll(@Valid List<ProductAttributeValueCreateRequest> requestList) {
        if (CollectionUtils.isEmpty(requestList)) {
            return Collections.emptyList();
        }

        boolean allProductIdsAreSame = requestList.stream()
                .map(ProductAttributeValueCreateRequest::productId)
                .distinct()
                .count() == 1;

        if (!allProductIdsAreSame) {
            throw new IllegalArgumentException("Tek seferde farklı ürünlere ait özellik değerleri güncellenemez !");
        }

        final String productId = requestList.get(0).productId();
        final List<ProductAttributeValue> existList = this.repository.findAllByProductIdOrderByProductAttributeId(productId);

        // Yeni listede olmayan attribute'leri sil
        for (ProductAttributeValue exist : existList) {
            boolean hasRequest = requestList
                .stream()
                .anyMatch(request -> exist.getProductAttribute().getId().compareTo(request.productAttributeId()) == 0);

            if (!hasRequest) {
                this.repository.delete(exist);
            }
        }

        final List<ProductAttributeValue> result = new ArrayList<>();

        // Olmayanları ekle, olanları güncelle
        for (ProductAttributeValueCreateRequest request : requestList) {
            ProductAttributeValue exist = existList.stream()
                    .filter(attributeValue -> attributeValue.getProductAttribute().getId().equals(request.productAttributeId()))
                    .findFirst()
                    .orElse(null);

            if (exist != null) {
                exist.setAttributeValue(request.attributeValue());
                exist = this.repository.save(exist);

                result.add(exist);
            } else {
                ProductAttributeValue productAttributeValue = this.mapper.toEntity(request);
                this.repository.save(productAttributeValue);

                result.add(productAttributeValue);
            }
        }

        return result
                .stream()
                .map(this.mapper::toCreateResponse)
                .toList();
    }
}

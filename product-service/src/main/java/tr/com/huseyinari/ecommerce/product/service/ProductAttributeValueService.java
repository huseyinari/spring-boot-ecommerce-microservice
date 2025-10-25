package tr.com.huseyinari.ecommerce.product.service;

import jakarta.validation.Valid;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ProductAttributeValueService {
    private final Logger logger = LoggerFactory.getLogger(ProductAttributeValueService.class);

    private final ProductAttributeValueRepository repository;
    private final ProductAttributeValueMapper mapper;

    @Transactional
    public void saveAll(@Valid List<ProductAttributeValueCreateRequest> requestList) {
        if (requestList == null || requestList.isEmpty()) {
            return;
        }

        boolean allProductIdsAreSame = requestList.stream()
                .map(ProductAttributeValueCreateRequest::productId)
                .distinct()
                .count() == 1;

        if (!allProductIdsAreSame) {
            throw new IllegalArgumentException("Tek seferde farklı ürünlere ait özellik değerleri güncellenemez !");
        }

        final String productId = requestList.get(0).productId();
        final List<ProductAttributeValue> existList = this.repository.findByProduct_Id(productId);

        // Yeni listede olmayanları sil
        for (ProductAttributeValue exist : existList) {
            boolean hasRequest = requestList
                .stream()
                .anyMatch(request -> exist.getProductAttribute().getId().compareTo(request.productAttributeId()) == 0);

            if (!hasRequest) {
                this.repository.delete(exist);
            }
        }

        // Olmayanları ekle, olanları güncelle
        for (ProductAttributeValueCreateRequest request : requestList) {
            ProductAttributeValue hasExistList = existList.stream()
                    .filter(attributeValue -> attributeValue.getProductAttribute().getId().equals(request.productAttributeId()))
                    .findFirst()
                    .orElse(null);

            if (hasExistList != null) {
                hasExistList.setAttributeValue(request.attributeValue());
                this.repository.save(hasExistList);
            } else {
                ProductAttributeValue productAttributeValue = this.mapper.toEntity(request);
                this.repository.save(productAttributeValue);
            }
        }
    }
}

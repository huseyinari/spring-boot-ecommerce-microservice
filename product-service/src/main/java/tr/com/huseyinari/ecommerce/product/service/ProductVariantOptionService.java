package tr.com.huseyinari.ecommerce.product.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantOption;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantOptionMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantOptionRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantOptionCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantOptionCreateResponse;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ProductVariantOptionService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantOptionService.class);

    private final ProductVariantOptionRepository repository;
    private final ProductVariantOptionMapper mapper;

    @Transactional
    public List<ProductVariantOptionCreateResponse> saveAll(@Valid List<ProductVariantOptionCreateRequest> requestList) {
        if (requestList == null || requestList.isEmpty()) {
            return Collections.emptyList();
        }

        boolean allVariantIdsAreSame = requestList.stream()
                .map(ProductVariantOptionCreateRequest::productVariantId)
                .distinct()
                .count() == 1;

        if (!allVariantIdsAreSame) {
            throw new IllegalArgumentException("Tek seferde farklı varyantlara ait seçenekler eklenemez !");
        }

        List<ProductVariantOption> productVariantOptionList = requestList.stream()
                .map(this.mapper::toEntity)
                .toList();

        for (ProductVariantOption productVariantOption : productVariantOptionList) {
            this.repository.save(productVariantOption);
        }

        return productVariantOptionList.stream()
                .map(this.mapper::toCreateResponse)
                .toList();
    }

    @Transactional
    public void deleteAllByProductVariantId(@NotNull Long productVariantId) {
        List<ProductVariantOption> productVariantOptionList = this.repository.findByProductVariant_Id(productVariantId);
        productVariantOptionList.forEach(this.repository::delete);
    }
}

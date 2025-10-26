package tr.com.huseyinari.ecommerce.product.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariant;
import tr.com.huseyinari.ecommerce.product.mapper.ProductVariantMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductVariantRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantOptionCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantUpdateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantSearchResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantUpdateResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ProductVariantService {
    private final Logger logger = LoggerFactory.getLogger(ProductVariantService.class);

    private final ProductVariantRepository repository;
    private final ProductVariantMapper mapper;
    private final ProductVariantOptionService productVariantOptionService;

    @Transactional(readOnly = true)
    public List<ProductVariantSearchResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    @Transactional
    public ProductVariantCreateResponse create(@Valid ProductVariantCreateRequest request) {
        ProductVariant productVariant = this.mapper.toEntity(request);
        productVariant = this.repository.save(productVariant);

        if (request.options() != null && !request.options().isEmpty()) {
            List<ProductVariantOptionCreateRequest> optionCreateRequestList = new ArrayList<>();

            for (String option : request.options()) {
                optionCreateRequestList.add(new ProductVariantOptionCreateRequest(productVariant.getId(), option));
            }

            this.productVariantOptionService.createAll(optionCreateRequestList);
        }

        return this.mapper.toCreateResponse(productVariant);
    }

    @Transactional
    public ProductVariantUpdateResponse update(@Valid ProductVariantUpdateRequest request) {
        ProductVariant exist = this.repository.findById(request.id()).orElseThrow(() -> new RuntimeException("Ürün varyantı bulunamadı"));

        if (!exist.getQueryName().equals(request.queryName())) {
            Optional<ProductVariant> optional = this.repository.findByQueryName(request.queryName());
            if (optional.isPresent()) {
                throw new RuntimeException("Sorgu adı daha önce kullanılmıştır !");
            }
        }

        this.mapper.fromUpdateRequestToEntity(request, exist);
        exist = this.repository.save(exist);

        // option'ların tamamını sil ve yeni gelenleri kaydet
        this.productVariantOptionService.deleteAllByProductVariantId(exist.getId());

        if (request.options() != null && !request.options().isEmpty()) {
            List<ProductVariantOptionCreateRequest> optionCreateRequestList = new ArrayList<>();

            for (String option : request.options()) {
                optionCreateRequestList.add(new ProductVariantOptionCreateRequest(exist.getId(), option));
            }
            this.productVariantOptionService.createAll(optionCreateRequestList);
        }

        return this.mapper.toUpdateResponse(exist);
    }
}

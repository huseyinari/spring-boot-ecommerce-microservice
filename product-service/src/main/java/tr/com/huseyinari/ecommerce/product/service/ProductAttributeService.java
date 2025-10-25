package tr.com.huseyinari.ecommerce.product.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttribute;
import tr.com.huseyinari.ecommerce.product.mapper.ProductAttributeMapper;
import tr.com.huseyinari.ecommerce.product.repository.ProductAttributeRepository;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeUpdateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeSearchResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeUpdateResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ProductAttributeService {
    private final Logger logger = LoggerFactory.getLogger(ProductAttributeService.class);

    private final ProductAttributeRepository repository;
    private final ProductAttributeMapper mapper;

    @Transactional(readOnly = true)
    public List<ProductAttributeSearchResponse> findAll() {
        return this.repository.findAllOrderByNameAsc()
                .stream()
                .map(this.mapper::toSearchResponse)
                .toList();
    }

    @Transactional
    public ProductAttributeCreateResponse create(@Valid ProductAttributeCreateRequest request) {
        ProductAttribute productAttribute = this.mapper.toEntity(request);
        productAttribute = this.repository.save(productAttribute);

        return this.mapper.toCreateResponse(productAttribute);
    }

    @Transactional
    public ProductAttributeUpdateResponse update(@Valid ProductAttributeUpdateRequest request) {
        ProductAttribute exist = this.repository.findById(request.id()).orElseThrow(() -> new RuntimeException("Ürün özelliği bulunamadı !"));

        if (!exist.getQueryName().equals(request.queryName())) {
            Optional<ProductAttribute> optional = this.repository.findByQueryName(request.queryName());
            if (optional.isPresent()) {
                throw new RuntimeException("Sorgu adı daha önce kullanılmıştır !");
            }
        }

        this.mapper.fromUpdateRequestToEntity(request, exist);
        exist = this.repository.save(exist);

        return this.mapper.toUpdateResponse(exist);
    }

    @Transactional
    public void delete(@NotNull Long id) {
        Optional<ProductAttribute> optional = this.repository.findById(id);

        if (optional.isPresent()) {
            this.repository.delete(optional.get());
        } else {
            throw new RuntimeException("Ürün özelliği bulunamadı !");
        }
    }
}

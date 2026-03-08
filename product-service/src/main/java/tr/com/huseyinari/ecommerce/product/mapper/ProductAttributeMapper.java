package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttribute;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeUpdateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeSearchPageableResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeSearchResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeUpdateResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductAttributeMapper {
    public ProductAttributeSearchResponse toSearchResponse(ProductAttribute productAttribute) {
        if (productAttribute == null) {
            return null;
        }

        return new ProductAttributeSearchResponse(
            productAttribute.getId(),
            productAttribute.getName(),
            productAttribute.getQueryName(),
            productAttribute.getDescription()
        );
    }

    public ProductAttribute toEntity(ProductAttributeCreateRequest request) {
        if (request == null) {
            return null;
        }

        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setName(request.name());
        productAttribute.setQueryName(request.queryName());
        productAttribute.setDescription(request.description());

        return productAttribute;
    }

    public ProductAttributeCreateResponse toCreateResponse(ProductAttribute productAttribute) {
        if (productAttribute == null) {
            return null;
        }

        return new ProductAttributeCreateResponse(
            productAttribute.getId(),
            productAttribute.getName(),
            productAttribute.getQueryName(),
            productAttribute.getDescription()
        );
    }

    public void fromUpdateRequestToEntity(ProductAttributeUpdateRequest request, ProductAttribute productAttribute) {
        if (productAttribute == null) {
            return;
        }
        if (request == null) {
            return;
        }

        productAttribute.setName(request.name());
        productAttribute.setQueryName(request.queryName());
        productAttribute.setDescription(request.description());
    }

    public ProductAttributeUpdateResponse toUpdateResponse(ProductAttribute productAttribute) {
        if (productAttribute == null) {
            return null;
        }

        return new ProductAttributeUpdateResponse(
            productAttribute.getId(),
            productAttribute.getName(),
            productAttribute.getQueryName(),
            productAttribute.getDescription()
        );
    }

    public ProductAttributeSearchPageableResponse toSearchPageableResponse(Page<ProductAttribute> pageResult) {
        if (pageResult == null) {
            return null;
        }

        List<ProductAttributeSearchResponse> searchResponseList = pageResult
                .getContent()
                .stream()
                .map(this::toSearchResponse)
                .toList();

        ProductAttributeSearchPageableResponse response = new ProductAttributeSearchPageableResponse();
        response.setItems(searchResponseList);
        response.setPage(pageResult.getNumber());
        response.setSize(pageResult.getSize());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setFirst(pageResult.isFirst());
        response.setLast(pageResult.isLast());

        return response;
    }
}

package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariant;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantOption;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantCreateRequest;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantUpdateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantSearchResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantUpdateResponse;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductVariantMapper {
    public ProductVariantSearchResponse toSearchResponse(ProductVariant productVariant) {
        if (productVariant == null) {
            return null;
        }

        return new ProductVariantSearchResponse(
            productVariant.getId(),
            productVariant.getName(),
            productVariant.getQueryName(),
            productVariant.getDescription(),
            productVariant.getDataType(),
            productVariant.getUiComponent(),
            productVariant.getMinValue(),
            productVariant.getMaxValue()
        );
    }

    public ProductVariant toEntity(ProductVariantCreateRequest request) {
        if (request == null) {
            return null;
        }

        ProductVariant productVariant = new ProductVariant();
        productVariant.setName(request.name());
        productVariant.setQueryName(request.queryName());
        productVariant.setDescription(request.description());
        productVariant.setDataType(request.dataType());
        productVariant.setUiComponent(request.uiComponent());
        productVariant.setMinValue(request.minValue());
        productVariant.setMaxValue(request.maxValue());

        return productVariant;
    }

    public ProductVariantCreateResponse toCreateResponse(ProductVariant productVariant) {
        if (productVariant == null) {
            return null;
        }

        List<String> options = new ArrayList<>();
        if (productVariant.getOptions() != null) {
            options = productVariant.getOptions().stream().map(ProductVariantOption::getOptionValue).toList();
        }

        return new ProductVariantCreateResponse(
            productVariant.getId(),
            productVariant.getName(),
            productVariant.getQueryName(),
            productVariant.getDescription(),
            productVariant.getDataType(),
            productVariant.getUiComponent(),
            productVariant.getMinValue(),
            productVariant.getMaxValue(),
            options
        );
    }

    public void fromUpdateRequestToEntity(ProductVariantUpdateRequest request, ProductVariant productVariant) {
        if (productVariant == null) {
            return;
        }
        if (request == null) {
            return;
        }

        productVariant.setName(request.name());
        productVariant.setQueryName(request.queryName());
        productVariant.setDescription(request.description());
        productVariant.setDataType(request.dataType());
        productVariant.setUiComponent(request.uiComponent());
        productVariant.setMinValue(request.minValue());
        productVariant.setMaxValue(request.maxValue());
    }

    public ProductVariantUpdateResponse toUpdateResponse(ProductVariant productVariant) {
        if (productVariant == null) {
            return null;
        }

        List<String> options = productVariant.getOptions().stream().map(ProductVariantOption::getOptionValue).toList();

        return new ProductVariantUpdateResponse(
            productVariant.getId(),
            productVariant.getName(),
            productVariant.getQueryName(),
            productVariant.getDescription(),
            productVariant.getDataType(),
            productVariant.getUiComponent(),
            productVariant.getMinValue(),
            productVariant.getMaxValue(),
            options
        );
    }
}

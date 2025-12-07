package tr.com.huseyinari.ecommerce.product.response;

import tr.com.huseyinari.ecommerce.product.enums.ProductVariantDataType;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantUiComponent;

import java.util.List;

public record ProductVariantUpdateResponse(
    Long id,
    String name,
    String queryName,
    String description,
    ProductVariantDataType dataType,
    ProductVariantUiComponent uiComponent,
    Integer minValue,
    Integer maxValue,
    Integer productVariantIndexJsonOrderNumber,
    List<String> options
) {}

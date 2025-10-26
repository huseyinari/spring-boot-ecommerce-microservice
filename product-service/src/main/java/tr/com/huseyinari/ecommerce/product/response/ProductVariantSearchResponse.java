package tr.com.huseyinari.ecommerce.product.response;

import tr.com.huseyinari.ecommerce.product.enums.ProductVariantDataType;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantUiComponent;

public record ProductVariantSearchResponse(
    Long id,
    String name,
    String queryName,
    String description,
    ProductVariantDataType dataType,
    ProductVariantUiComponent uiComponent,
    Integer minValue,
    Integer maxValue
) {}

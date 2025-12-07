package tr.com.huseyinari.ecommerce.product.response;

import java.util.List;

public record ProductVariantIndexCreateResponse(
    List<ProductVariantValueCreateResponse> variantValueCombinations
) {}

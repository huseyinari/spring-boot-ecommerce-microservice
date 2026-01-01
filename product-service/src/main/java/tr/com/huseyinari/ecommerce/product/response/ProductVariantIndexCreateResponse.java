package tr.com.huseyinari.ecommerce.product.response;

import java.math.BigDecimal;
import java.util.Map;

public record ProductVariantIndexCreateResponse(
        Long id,
        Map<String, Object> variantValueIndex,
        String skuCode,
        Integer stock,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal discountedPrice,
        Integer queryOrder,
        String productId
) {}

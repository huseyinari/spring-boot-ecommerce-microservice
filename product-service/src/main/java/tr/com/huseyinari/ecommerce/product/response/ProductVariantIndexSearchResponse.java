package tr.com.huseyinari.ecommerce.product.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductVariantIndexSearchResponse(
        Long id,
        String productId,
        String productName,
        List<String> imageUrls,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal discountedPrice
) {}

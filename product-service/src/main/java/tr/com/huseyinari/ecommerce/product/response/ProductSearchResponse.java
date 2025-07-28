package tr.com.huseyinari.ecommerce.product.response;

import java.math.BigDecimal;
import java.util.Set;

public record ProductSearchResponse (
    String id,
    String name,
    String description,
    String skuCode,
    BigDecimal price,
    BigDecimal discount,
    BigDecimal  discountedPrice,
    Set<String> imageUrls
) {}

package tr.com.huseyinari.ecommerce.product.response;

import java.math.BigDecimal;
import java.util.Set;

public record ProductCreateResponse(
    String id,
    String name,
    String description,
    String skuCode,
    BigDecimal price,
    BigDecimal discount,
    BigDecimal discountedPrice,
    Set<Long> imageStorageIds
) {}

package tr.com.huseyinari.ecommerce.product.response;

import java.math.BigDecimal;

public record ProductMostInspectedTodayResponse(
    String id,
    String name,
    BigDecimal price,
    BigDecimal discount,
    BigDecimal discountedPrice,
    String imageUrl,
    Long viewCount
) {}

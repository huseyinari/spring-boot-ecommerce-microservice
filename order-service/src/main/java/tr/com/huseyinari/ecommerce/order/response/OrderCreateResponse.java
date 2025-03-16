package tr.com.huseyinari.ecommerce.order.response;

import java.math.BigDecimal;

public record OrderCreateResponse(
    Long id,
    String orderNumber,
    String skuCode,
    BigDecimal price,
    Integer quantity
) {}

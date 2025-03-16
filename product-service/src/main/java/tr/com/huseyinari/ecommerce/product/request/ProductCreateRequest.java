package tr.com.huseyinari.ecommerce.product.request;

import java.math.BigDecimal;


public record ProductCreateRequest(
    String name,
    String description,
    BigDecimal price,
    Long categoryId
) {}

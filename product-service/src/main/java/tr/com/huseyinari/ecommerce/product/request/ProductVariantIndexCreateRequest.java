package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public record ProductVariantIndexCreateRequest(
    @NotBlank(message = "Ürün alanı zorunludur.")
    String productId,

    @NotEmpty(message = "Ürün varyantı kombinasyonu zorunludur.")
    List<@NotEmpty(message = "Ürün varyantı değerleri zorunludur.")List<Long>> productVariantValueIdCombinations,

    String skuCode,
    Integer stock,
    BigDecimal price,
    BigDecimal discount,
    Integer queryOrder
) {}



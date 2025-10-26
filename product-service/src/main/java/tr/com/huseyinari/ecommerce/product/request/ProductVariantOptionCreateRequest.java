package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductVariantOptionCreateRequest(
    @NotNull(message = "Ürün varyantı alanı zorunludur.")
    @Min(value = 1, message = "Ürün varyantı alanı geçersiz.")
    Long productVariantId,

    @NotBlank(message = "Değer alanı zorunludur.")
    String optionValue
) {}

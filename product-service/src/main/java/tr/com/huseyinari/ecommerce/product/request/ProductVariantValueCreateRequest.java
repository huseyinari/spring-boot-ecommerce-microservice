package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductVariantValueCreateRequest(
    @NotNull(message = "Ürün varyantı alanı zorunludur.")
    @Min(value = 1, message = "Geçersiz ürün varyantı.")
    Long productVariantId,

//    @NotBlank(message = "Ürün alanı zorunludur.") - variant_value'lar ürün oluşturulma aşamasında kaydedileceği için productId alanı servis içerisinde doldurulacaktır.
    String productId,

    @NotBlank(message = "Varyant değeri alanı zorunludur.")
    String variantValue
) {}

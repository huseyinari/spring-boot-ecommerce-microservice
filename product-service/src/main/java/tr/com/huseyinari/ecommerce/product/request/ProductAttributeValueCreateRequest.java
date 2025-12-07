package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductAttributeValueCreateRequest(
    @NotNull(message = "Ürün özelliği alanı zorunludur.")
    @Min(value = 1, message = "Geçersiz ürün özelliği")
    Long productAttributeId,

//    @NotBlank(message = "Ürün alanı zorunludur.") - attribute_value'lar ürün oluşturulma aşamasında kaydedileceği için productId alanı servis içerisinde doldurulacaktır.
    String productId,

    @NotBlank(message = "Özellik değeri zorunludur.")
    String attributeValue
) {}

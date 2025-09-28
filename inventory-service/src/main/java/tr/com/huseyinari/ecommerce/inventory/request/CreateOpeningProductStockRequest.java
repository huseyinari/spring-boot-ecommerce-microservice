package tr.com.huseyinari.ecommerce.inventory.request;

import jakarta.validation.constraints.NotBlank;

public record CreateOpeningProductStockRequest (
    @NotBlank(message = "Sku code bilgisi zorunludur.")
    String skuCode,
    @NotBlank(message = "İşlemi yapan kullanıcı bilinmediği işlem yapılamaz.")
    String createdBy
) {}

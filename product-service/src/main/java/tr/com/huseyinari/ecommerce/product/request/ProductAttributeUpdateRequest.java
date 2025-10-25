package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductAttributeUpdateRequest(
    @NotNull(message = "Id alanı zorunludur.")
    @Min(value = 1, message = "Id alanı 1'den küçük olamaz.")
    Long id,

    @NotBlank(message = "Tanım alanı zorunludur.")
    String name,

    @NotBlank(message = "Sorgu adı alanı zorunludur.")
    String queryName,

    String description
) {}

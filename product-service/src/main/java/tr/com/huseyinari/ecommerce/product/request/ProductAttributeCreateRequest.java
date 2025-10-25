package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.NotBlank;

public record ProductAttributeCreateRequest(
    @NotBlank(message = "Tanım alanı zorunludur.")
    String name,
    @NotBlank(message = "Sorgu adı alanı zorunludur.")
    String queryName,
    String description
) {}

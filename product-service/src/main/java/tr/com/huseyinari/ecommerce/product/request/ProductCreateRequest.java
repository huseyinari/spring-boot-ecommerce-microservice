package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record ProductCreateRequest(
    @NotBlank(message = "Lütfen ürün ismi giriniz.")
    String name,
    String description,
    @NotNull(message = "Lütfen ürün fiyatı giriniz.")
    BigDecimal price,
    @NotNull(message = "Lütfen kategori seçiniz.")
    Long categoryId
) {}

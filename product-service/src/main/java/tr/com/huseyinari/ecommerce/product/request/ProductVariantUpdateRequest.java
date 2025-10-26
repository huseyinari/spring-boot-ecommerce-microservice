package tr.com.huseyinari.ecommerce.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantDataType;
import tr.com.huseyinari.ecommerce.product.enums.ProductVariantUiComponent;

import java.util.List;

public record ProductVariantUpdateRequest(
    @NotNull(message = "Id alanı zorunludur.")
    @Min(value = 1, message = "Id alanı geçersiz.")
    Long id,
    @NotBlank(message = "Tanım alanı zorunludur.")
    String name,
    @NotBlank(message = "Sorgu adı zorunludur.")
    String queryName,
    String description,
    @NotNull(message = "Veri tipi alanı zorunludur.")
    ProductVariantDataType dataType,
    @NotNull(message = "UI bileşeni alanı zorunludur.")
    ProductVariantUiComponent uiComponent,
    Integer minValue,
    Integer maxValue,
    List<String> options
) {}

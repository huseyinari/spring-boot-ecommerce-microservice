package tr.com.huseyinari.ecommerce.category.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterType;
import tr.com.huseyinari.ecommerce.category.enums.CategoryProductsFilterUiComponent;

public record CategoryProductsFilterOptionUpdateRequest(
    @NotNull(message = "Filtre güncelleme işlemi için filtre kimliği zorunludur.")
    @Min(value = 1, message = "Geçerli bir filtre kimliği seçiniz.")
    Long id,

    @NotBlank(message = "Filtre adı boş bırakılamaz.")
    String name,

    @NotBlank(message = "Filtre sorgu adı boş bırakılamaz.")
    String queryName,

    @NotNull(message = "Filtre tipi boş bırakılamaz.")
    CategoryProductsFilterType filterType,

    @NotNull(message = "Filtre arayüz bileşeni boş bırakılamaz.")
    CategoryProductsFilterUiComponent uiComponent,

    Integer maxFilterOption,

    @NotNull(message = "Kategori seçiniz.")
    @Min(value = 1, message = "Geçerli bir kategori seçiniz.")
    Long categoryId
) {}

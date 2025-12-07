package tr.com.huseyinari.ecommerce.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Product Create Request")
public record ProductCreateRequest(
    @Schema(description = "Ürün Adı", example = "Iphone 14")
    @NotBlank(message = "Lütfen ürün ismi giriniz.")
    String name,

    @Schema(description = "Detaylı açıklama", example = "Iphone 14 256 GB")
    String description,

    @Schema(description = "Fiyat", example = "1000")
    @NotNull(message = "Lütfen ürün fiyatı giriniz.")
    BigDecimal price,

    @Schema(description = "İndirim miktarı", example = "199.99")
    BigDecimal discount,

    @Schema(description = "Ürün kategorisi", example = "100000")
    @NotNull(message = "Lütfen kategori seçiniz.")
    Long categoryId,

    @Schema(description = "Ürün attribute değerleri", example = "[{\"productAttributeId\": 104, \"attributeValue\": \"14.6 inc\"}]")
    List<ProductAttributeValueCreateRequest> attributeValues,

    @Schema(description = "Ürün varyant değerleri", example = "[{\"productVariantId\": 1, \"variantValue\": \"Beyaz\"}]")
    List<ProductVariantValueCreateRequest> variantValues
) {}

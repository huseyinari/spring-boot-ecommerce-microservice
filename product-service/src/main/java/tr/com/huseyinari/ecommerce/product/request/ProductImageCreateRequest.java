package tr.com.huseyinari.ecommerce.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Ürün ve resim ilişkilendirmek için istenen veriler")
public record ProductImageCreateRequest(
    @Schema(description = "Ürün Id", example = "a1b2c-d3e4f5-k6l8o7-e3t76-qs29he")
    @NotBlank(message = "Lütfen ürün Id giriniz.")
    String productId,
    @Schema(description = "Depolama Nesnesi Id", example = "123456")
    @NotNull(message = "Lütfen resim Id giriniz.")
    Long storageObjectId
) {}

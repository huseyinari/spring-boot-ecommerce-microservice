package tr.com.huseyinari.ecommerce.category.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CategoryUpdateRequest(
    @NotNull(message = "Kategori seçimi zorunludur.")
    @Min(value = 1, message = "Lütfen geçerli bir kategori seçiniz.")
    Long id,

    @NotBlank(message = "Kategori adı zorunludur.")
    String name,

    Long parentId,

    MultipartFile image
) {}

package tr.com.huseyinari.ecommerce.category.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record CategoryCreateRequest(
        @NotBlank(message = "Kategori adı zorunludur.")
        String name,
        Long parentId,
        MultipartFile image
) {}

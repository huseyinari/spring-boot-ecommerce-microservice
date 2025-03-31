package tr.com.huseyinari.ecommerce.auth.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "Kullanıcı adı alanı boş olamaz.")
    String username,
    @NotBlank(message = "Parola alanı boş olamaz.")
    String password
) {}

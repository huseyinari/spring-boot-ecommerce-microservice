package tr.com.huseyinari.ecommerce.auth.request;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequest(
    @NotNull(message = "refreshToken boş olamaz.")
    String refreshToken
) {}

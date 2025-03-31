package tr.com.huseyinari.ecommerce.auth.response;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {}

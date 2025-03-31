package tr.com.huseyinari.ecommerce.auth.response;

public record LoginResponse(
    String tokenType,
    String accessToken,
    String refreshToken,
    Integer expiresIn,
    Integer refreshExpiresIn
) {}

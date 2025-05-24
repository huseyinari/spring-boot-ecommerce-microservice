package tr.com.huseyinari.ecommerce.auth.response;

public record RegisterResponse(
    String userId,
    String username,
    String firstName,
    String lastName,
    String email
) {}

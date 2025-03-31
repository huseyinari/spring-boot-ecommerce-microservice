package tr.com.huseyinari.ecommerce.auth.response;

public record RegisterResponse(
    String username,
    String firstName,
    String lastName,
    String email
) {}

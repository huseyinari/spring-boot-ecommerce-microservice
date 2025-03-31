package tr.com.huseyinari.ecommerce.auth.request;

public record LoginRequest(
    String username,
    String password
) {}

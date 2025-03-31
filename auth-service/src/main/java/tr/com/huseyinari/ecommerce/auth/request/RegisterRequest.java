package tr.com.huseyinari.ecommerce.auth.request;

public record RegisterRequest(
    String userName,
    String password,
    String firstName,
    String lastName,
    String email
) {}

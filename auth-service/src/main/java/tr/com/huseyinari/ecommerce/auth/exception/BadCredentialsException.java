package tr.com.huseyinari.ecommerce.auth.exception;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException() {
        super("Kullanıcı adı veya şifre yanlış");
    }
}

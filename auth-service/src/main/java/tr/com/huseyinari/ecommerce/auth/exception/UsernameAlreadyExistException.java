package tr.com.huseyinari.ecommerce.auth.exception;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException() {
        super("Hesap zaten kullanılıyor");
    }
}

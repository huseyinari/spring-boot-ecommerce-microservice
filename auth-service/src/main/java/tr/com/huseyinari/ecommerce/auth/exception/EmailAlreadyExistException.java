package tr.com.huseyinari.ecommerce.auth.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super("E-Posta adresi zaten kullanılıyor");
    }
}

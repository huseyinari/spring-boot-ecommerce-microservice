package tr.com.huseyinari.ecommerce.auth.exception;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException() {
        super("Kullanıcı hesabı aktif değil");
    }
}

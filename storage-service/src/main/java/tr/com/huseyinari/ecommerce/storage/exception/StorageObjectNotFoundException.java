package tr.com.huseyinari.ecommerce.storage.exception;

public class StorageObjectNotFoundException extends RuntimeException {
    public StorageObjectNotFoundException() {
        super("Dosya bulunamadı !");
    }
}

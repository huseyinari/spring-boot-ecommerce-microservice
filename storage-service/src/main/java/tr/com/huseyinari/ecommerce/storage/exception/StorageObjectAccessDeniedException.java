package tr.com.huseyinari.ecommerce.storage.exception;

public class StorageObjectAccessDeniedException extends RuntimeException {
    public StorageObjectAccessDeniedException() {
        super("Dosyaya erişim izniniz bulunmamaktadır !");
    }
}

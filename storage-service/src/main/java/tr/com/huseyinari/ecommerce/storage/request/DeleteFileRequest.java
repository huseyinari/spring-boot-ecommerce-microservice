package tr.com.huseyinari.ecommerce.storage.request;

public interface DeleteFileRequest {
    String getFileName();           // Silinecek dosyanın adı
    String getStorageName();        // Silinecek dosyanın bulunduğu depolama alanı (Örneğin S3 için bucket adı, Fiziksel makine için klasör adı ...)
}

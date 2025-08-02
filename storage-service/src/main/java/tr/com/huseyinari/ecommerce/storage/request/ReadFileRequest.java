package tr.com.huseyinari.ecommerce.storage.request;

public interface ReadFileRequest {
    String getFileName();           // Okunacak dosyanın adı
    String getStorageName();        // Okunacak dosyanın bulunduğu depolama alanı (Örneğin S3 için bucket adı, Fiziksel makine için klasör adı ...)
}

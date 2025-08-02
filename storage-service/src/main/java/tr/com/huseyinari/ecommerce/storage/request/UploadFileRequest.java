package tr.com.huseyinari.ecommerce.storage.request;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileRequest {
    MultipartFile getMultipartFile();   // Yüklenecek dosya
    String getStorageName();            // Dosyanın yükleneceği depolama alanı (Örneğin S3 için bucket adı, Fiziksel makine için klasör adı ...)
}

package tr.com.huseyinari.ecommerce.storage.response;

public interface UploadFileResponse {
    String getFileName();
    String getExtension();
    Long getFileSize();
    String getStorageName();
}

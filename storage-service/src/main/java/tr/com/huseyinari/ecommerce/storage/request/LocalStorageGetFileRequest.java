package tr.com.huseyinari.ecommerce.storage.request;

public record LocalStorageGetFileRequest (
    String fileName,
    String directoryName
) {}

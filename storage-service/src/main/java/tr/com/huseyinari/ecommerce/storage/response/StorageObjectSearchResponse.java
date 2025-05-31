package tr.com.huseyinari.ecommerce.storage.response;

public record StorageObjectSearchResponse(
    Long id,
    String fileName,
    String storageName,
    Long fileSize,
    String extension
) {}

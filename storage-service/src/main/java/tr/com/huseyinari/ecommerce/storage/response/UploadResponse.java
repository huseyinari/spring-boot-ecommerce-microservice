package tr.com.huseyinari.ecommerce.storage.response;

public record UploadResponse (
    Long storageObjectId,
    String fileName,
    String extension,
    Long fileSize,
    String storageName,
    String ownerId,
    boolean privateAccess
) {}

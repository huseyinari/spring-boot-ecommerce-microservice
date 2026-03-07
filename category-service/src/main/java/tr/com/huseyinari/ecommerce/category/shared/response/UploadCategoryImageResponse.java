package tr.com.huseyinari.ecommerce.category.shared.response;

public record UploadCategoryImageResponse(
    Long storageObjectId,
    String fileName,
    String extension,
    Long fileSize,
    String storageName,
    String ownerId
) {}

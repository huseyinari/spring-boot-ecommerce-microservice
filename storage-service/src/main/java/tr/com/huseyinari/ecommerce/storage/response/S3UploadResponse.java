package tr.com.huseyinari.ecommerce.storage.response;

public record S3UploadResponse(
    String fileName,
    String extension,
    Long fileSize,
    String bucketName
) {}

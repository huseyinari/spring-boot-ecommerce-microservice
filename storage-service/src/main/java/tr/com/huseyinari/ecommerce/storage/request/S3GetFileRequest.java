package tr.com.huseyinari.ecommerce.storage.request;

public record S3GetFileRequest(
    String fileName,
    String bucketName
) {}

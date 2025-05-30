package tr.com.huseyinari.ecommerce.storage.request;

import org.springframework.web.multipart.MultipartFile;

public record S3UploadRequest (
    MultipartFile multipartFile,
    String bucketName
) {}

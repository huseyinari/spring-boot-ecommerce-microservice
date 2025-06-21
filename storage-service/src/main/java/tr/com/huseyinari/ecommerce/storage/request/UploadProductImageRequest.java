package tr.com.huseyinari.ecommerce.storage.request;

import org.springframework.web.multipart.MultipartFile;

public record UploadProductImageRequest(
    MultipartFile multipartFile
    // String storageName,
//    boolean privateAccess
) {}

package tr.com.huseyinari.ecommerce.storage.request;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
public class S3UploadFileRequest implements UploadFileRequest {
    private final MultipartFile multipartFile;
    private final String bucketName;

    @Override
    public MultipartFile getMultipartFile() {
        return this.multipartFile;
    }

    @Override
    public String getStorageName() {
        return this.bucketName;
    }
}

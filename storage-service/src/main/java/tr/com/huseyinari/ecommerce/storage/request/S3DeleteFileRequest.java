package tr.com.huseyinari.ecommerce.storage.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3DeleteFileRequest implements DeleteFileRequest {
    private final String fileName;
    private final String bucketName;

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getStorageName() {
        return this.bucketName;
    }
}

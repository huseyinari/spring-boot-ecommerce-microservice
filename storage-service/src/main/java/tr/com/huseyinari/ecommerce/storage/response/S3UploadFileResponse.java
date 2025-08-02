package tr.com.huseyinari.ecommerce.storage.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3UploadFileResponse implements UploadFileResponse {
    private String fileName;
    private String extension;
    private Long fileSize;
    private String bucketName;

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getExtension() {
        return this.extension;
    }

    @Override
    public Long getFileSize() {
        return this.fileSize;
    }

    @Override
    public String getStorageName() {
        return this.bucketName;
    }
}

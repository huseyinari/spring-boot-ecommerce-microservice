package tr.com.huseyinari.ecommerce.storage.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocalStorageDeleteFileRequest implements DeleteFileRequest {
    private final String fileName;
    private final String directoryName;

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getStorageName() {
        return this.directoryName;
    }
}

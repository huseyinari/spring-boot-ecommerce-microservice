package tr.com.huseyinari.ecommerce.storage.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocalStorageReadFileResponse implements ReadFileResponse {
    private final byte[] fileContent;

    @Override
    public byte[] getFileContent() {
        return this.fileContent;
    }
}

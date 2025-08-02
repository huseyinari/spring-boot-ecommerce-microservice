package tr.com.huseyinari.ecommerce.storage.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3ReadFileResponse implements ReadFileResponse {
    private final byte[] fileContent;

    @Override
    public byte[] getFileContent() {
        return this.fileContent;
    }
}

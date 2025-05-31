package tr.com.huseyinari.ecommerce.storage.mapper;

import tr.com.huseyinari.ecommerce.storage.domain.StorageObject;
import tr.com.huseyinari.ecommerce.storage.response.StorageObjectSearchResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadResponse;

public class StorageMapper {
    private StorageMapper() {

    }

    public static StorageObjectSearchResponse toSearchResponse(StorageObject storageObject) {
        return new StorageObjectSearchResponse(
            storageObject.getId(),
            storageObject.getFileName(),
            storageObject.getStorageName(),
            storageObject.getFileSize(),
            storageObject.getExtension()
        );
    }

    public static UploadResponse toUploadResponse(StorageObject storageObject) {
        return new UploadResponse(
            storageObject.getId(),
            storageObject.getFileName(),
            storageObject.getExtension(),
            storageObject.getFileSize(),
            storageObject.getStorageName(),
            storageObject.getOwnerId(),
            storageObject.isPrivateAccess()
        );
    }
}

package tr.com.huseyinari.ecommerce.storage.mapper;

import tr.com.huseyinari.ecommerce.storage.domain.StorageObject;
import tr.com.huseyinari.ecommerce.storage.request.StorageObjectCreateRequest;
import tr.com.huseyinari.ecommerce.storage.response.StorageObjectCreateResponse;
import tr.com.huseyinari.ecommerce.storage.response.StorageObjectSearchResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadProductImageResponse;

public class StorageMapper {
    private StorageMapper() {

    }

    public static StorageObjectSearchResponse toSearchResponse(StorageObject storageObject) {
        return new StorageObjectSearchResponse(
            storageObject.getId(),
            storageObject.getFileName(),
            storageObject.getStorageName(),
            storageObject.getFileSize(),
            storageObject.getExtension(),
            storageObject.getType()
        );
    }

    public static StorageObject toEntity(StorageObjectCreateRequest request) {
        return StorageObject
                .builder()
                .fileName(request.fileName())
                .fileSize(request.fileSize())
                .extension(request.extension())
                .storageName(request.storageName())
                .privateAccess(request.privateAccess())
                .ownerId(request.ownerId())
                .type(request.type())
                .build();
    }

    public static StorageObjectCreateResponse toCreateResponse(StorageObject storageObject) {
        return new StorageObjectCreateResponse(
            storageObject.getId(),
            storageObject.getFileName(),
            storageObject.getStorageName(),
            storageObject.getFileSize(),
            storageObject.getExtension(),
            storageObject.getOwnerId(),
            storageObject.getType(),
            storageObject.isPrivateAccess()
        );
    }

    public static UploadProductImageResponse toUploadProductImageResponse(StorageObjectCreateResponse response) {
        return new UploadProductImageResponse(
            response.id(),
            response.fileName(),
            response.extension(),
            response.fileSize(),
            response.storageName(),
            response.ownerId()
        );
    }
}

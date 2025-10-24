package tr.com.huseyinari.ecommerce.storage.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.storage.domain.StorageObject;
import tr.com.huseyinari.ecommerce.storage.request.StorageObjectCreateRequest;
import tr.com.huseyinari.ecommerce.storage.response.StorageObjectCreateResponse;
import tr.com.huseyinari.ecommerce.storage.response.StorageObjectSearchResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadProductImageResponse;

@Component
@RequiredArgsConstructor
public class StorageObjectMapper {
    public StorageObjectSearchResponse toSearchResponse(StorageObject storageObject) {
        if (storageObject == null) {
            return null;
        }

        return new StorageObjectSearchResponse(
            storageObject.getId(),
            storageObject.getFileName(),
            storageObject.getStorageName(),
            storageObject.getFileSize(),
            storageObject.getExtension(),
            storageObject.getType()
        );
    }

    public StorageObject toEntity(StorageObjectCreateRequest request) {
        if (request == null) {
            return null;
        }

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

    public StorageObjectCreateResponse toCreateResponse(StorageObject storageObject) {
        if (storageObject == null) {
            return null;
        }

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

    public UploadProductImageResponse toUploadProductImageResponse(StorageObjectCreateResponse response) {
        if (response == null) {
            return null;
        }

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

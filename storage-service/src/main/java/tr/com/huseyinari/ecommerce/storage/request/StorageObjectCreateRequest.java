package tr.com.huseyinari.ecommerce.storage.request;

import tr.com.huseyinari.ecommerce.storage.enums.StorageObjectType;

public record StorageObjectCreateRequest (
    String fileName,
    String storageName,
    Long fileSize,
    String extension,
    String ownerId,
    StorageObjectType type,
    boolean privateAccess
){}

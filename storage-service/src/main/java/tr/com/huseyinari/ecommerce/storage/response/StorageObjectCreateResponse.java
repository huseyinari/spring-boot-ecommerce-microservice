package tr.com.huseyinari.ecommerce.storage.response;

import tr.com.huseyinari.ecommerce.storage.enums.StorageObjectType;

public record StorageObjectCreateResponse (
    Long id,
    String fileName,
    String storageName,
    Long fileSize,
    String extension,
    String ownerId,
    StorageObjectType type,
    boolean privateAccess
) {}

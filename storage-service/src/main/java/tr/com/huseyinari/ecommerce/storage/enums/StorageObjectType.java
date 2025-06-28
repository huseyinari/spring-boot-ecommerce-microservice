package tr.com.huseyinari.ecommerce.storage.enums;

import lombok.Getter;

@Getter
public enum StorageObjectType {
    LOCAL("LOCAL"),
    S3("S3");

    private final String name;
    StorageObjectType(String name) {
        this.name = name;
    }
}

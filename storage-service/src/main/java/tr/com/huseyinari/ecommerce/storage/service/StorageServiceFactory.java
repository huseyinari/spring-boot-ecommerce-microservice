package tr.com.huseyinari.ecommerce.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.storage.enums.StorageObjectType;

@Component
@RequiredArgsConstructor
public class StorageServiceFactory {
    private final LocalStorageService localStorageService;
    private final S3Service s3Service;

    public StorageService getStorageService(StorageObjectType type) {
        if (type == null) {
            throw new RuntimeException("Lütfen bir saklama alanı türü belirtiniz !");
        }

        return switch (type) {
            case LOCAL -> this.localStorageService;
            case S3 -> this.s3Service;
        };
    }
}

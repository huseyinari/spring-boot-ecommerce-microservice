package tr.com.huseyinari.ecommerce.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.storage.request.LocalStorageGetFileRequest;
import tr.com.huseyinari.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class LocalStorageService {
    private final ResourceLoader resourceLoader;
    private final String localStorageDirectoryName = "local-storage";

    public byte[] getFileContent(LocalStorageGetFileRequest request) {
        final String fileName = request.fileName();
        final String directoryName = request.directoryName();

        String location = "classpath:" + this.localStorageDirectoryName + "/" + directoryName + "/" + fileName;
        Resource resource = resourceLoader.getResource(location);

        if (resource.exists() && resource.isFile()) {
            try {
                InputStream inputStream = resource.getInputStream();
                return IOUtils.toByteArray(inputStream);
            } catch (IOException exception) {
                throw new RuntimeException("Dosya okunurken hata oluştu !");
            }
        } else {
            throw new RuntimeException("Dosya Bulunamadı !");
        }
    }
}

package tr.com.huseyinari.ecommerce.storage.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tr.com.huseyinari.ecommerce.storage.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.storage.request.ReadFileRequest;
import tr.com.huseyinari.ecommerce.storage.request.UploadFileRequest;
import tr.com.huseyinari.ecommerce.storage.response.LocalStorageReadFileResponse;
import tr.com.huseyinari.ecommerce.storage.response.LocalStorageUploadFileResponse;
import tr.com.huseyinari.ecommerce.storage.response.ReadFileResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadFileResponse;
import tr.com.huseyinari.utils.IOUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {
    private static final Logger logger = LoggerFactory.getLogger(LocalStorageService.class);
    private final ECommerceConfigurationProperties configurationProperties;

    @Override
    public UploadFileResponse uploadFile(UploadFileRequest request) {
        if (request.getMultipartFile() == null || request.getMultipartFile().isEmpty()) {
            throw new RuntimeException("Dosya seçilmedi.");
        }
        if (StringUtils.isBlank(request.getStorageName())) {
            throw new RuntimeException("Depolama adı boş olamaz.");
        }

        final MultipartFile multipartFile = request.getMultipartFile();
        final String directoryName = request.getStorageName();

        final Long maxUploadFileSize = this.configurationProperties.getMaxUploadFileSize();

        if (maxUploadFileSize.compareTo(multipartFile.getSize()) < 0) {
            throw new RuntimeException("Dosya boyutu çok büyük !");
        }

        final String originalFileName = multipartFile.getOriginalFilename();

        if (StringUtils.isBlank(originalFileName) || !originalFileName.contains(".")) {
            throw new RuntimeException("Geçersiz dosya adı !");
        }

        final String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        final long fileSize = multipartFile.getSize();  // byte

        final String localStorageFilePath = this.configurationProperties.getLocalStorageFilePath();
        final String newFileName = UUID.randomUUID() + "." + extension;

        File file = null;

        try {
            final File directory = new File(localStorageFilePath + File.separator + directoryName);
            if (!directory.exists()) {
                boolean isCreated = directory.mkdirs();
                if (!isCreated) {
                    throw new RuntimeException("Dosya yükleme işlemi başarısız. Depolama dizini oluşturulamadı !");
                }
            }

            file = new File(directory, newFileName);
            multipartFile.transferTo(file);

            logger.info("Dosya başarıyla yüklendi. Dosya: {}", file.getPath());

            return new LocalStorageUploadFileResponse(newFileName, extension, fileSize, directoryName);
        } catch (IOException exception) {
            logger.error("Dosya yükleme işlemi başarısız. Exception: {}", exception.getMessage());
            throw new RuntimeException("Dosya yükleme işlemi başarısız. " + exception.getMessage());
        }
    }

    @Override
    public ReadFileResponse getFileContent(ReadFileRequest request) {
        final String localStorageFilePath = this.configurationProperties.getLocalStorageFilePath();
        final String directoryName = request.getStorageName();
        final String fileName = request.getFileName();

        final String filePath = localStorageFilePath + File.separator + directoryName + File.separator + fileName;
        final File targetFile = new File(filePath);

        if (targetFile.exists() && targetFile.isFile()) {
            try (FileInputStream inputStream = new FileInputStream(targetFile)) {
                byte[] fileContent = IOUtils.toByteArray(inputStream);
                return new LocalStorageReadFileResponse(fileContent);
            } catch (IOException exception) {
                throw new RuntimeException("Dosya okunurken hata oluştu !");
            }
        } else {
            throw new RuntimeException("Dosya Bulunamadı !");
        }
    }
}

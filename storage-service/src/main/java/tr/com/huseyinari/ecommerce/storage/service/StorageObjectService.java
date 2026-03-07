package tr.com.huseyinari.ecommerce.storage.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.ecommerce.storage.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.storage.domain.StorageObject;
import tr.com.huseyinari.ecommerce.storage.enums.StorageObjectType;
import tr.com.huseyinari.ecommerce.storage.exception.StorageObjectAccessDeniedException;
import tr.com.huseyinari.ecommerce.storage.exception.StorageObjectNotFoundException;
import tr.com.huseyinari.ecommerce.storage.mapper.StorageObjectMapper;
import tr.com.huseyinari.ecommerce.storage.repository.StorageObjectRepository;
import tr.com.huseyinari.ecommerce.storage.request.*;
import tr.com.huseyinari.ecommerce.storage.response.*;
import tr.com.huseyinari.springweb.rest.RequestUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageObjectService {
    private static final Logger logger = LoggerFactory.getLogger(StorageObjectService.class);

    private final StorageObjectRepository repository;
    private final StorageObjectMapper mapper;
    private final StorageServiceFactory storageServiceFactory;
    private final ECommerceConfigurationProperties configurationProperties;

    @Transactional(readOnly = true)
    public StorageObjectSearchResponse findOne(Long id) {
        StorageObject storageObject = this.repository.findById(id).orElseThrow(StorageObjectNotFoundException::new);

        if (storageObject.isPrivateAccess()) {
            final String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElse(null);

            if (!storageObject.getOwnerId().equals(currentUserId)) {
                throw new StorageObjectAccessDeniedException();
            }
        }

        return this.mapper.toSearchResponse(storageObject);
    }

    @Transactional
    public StorageObjectCreateResponse create(StorageObjectCreateRequest request) {
        StorageObject storageObject = this.mapper.toEntity(request);
        storageObject = this.repository.save(storageObject);

        return this.mapper.toCreateResponse(storageObject);
    }

    @Transactional(readOnly = true)
    public FileContentResponse getFileContent(Long id) {
        StorageObjectSearchResponse storageObject = this.findOne(id);

        StorageService storageService = this.storageServiceFactory.getStorageService(storageObject.type());

        ReadFileRequest request = this.toReadFileRequest(storageObject);
        ReadFileResponse response = storageService.getFileContent(request);

        return new FileContentResponse(response.getFileContent(), storageObject.fileName(), storageObject.extension());
    }

    @Transactional(readOnly = true)
    public FileContentBase64Response getFileContentBase64(Long id) {
        final FileContentResponse fileContentResponse = this.getFileContent(id);

        final String base64 = StringUtils.encodeBase64(fileContentResponse.content());
        final String fileName = fileContentResponse.fileName();
        final String extension = fileContentResponse.extension();

        return new FileContentBase64Response(base64, fileName, extension);
    }

    @Transactional
    public void delete(Long id) {
        final String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow();

        Optional<StorageObject> optional = this.repository.findById(id);
        if (optional.isEmpty()) {
            throw new StorageObjectNotFoundException();
        }

        final StorageObject storageObject = optional.get();

        if (!currentUserId.equals(storageObject.getOwnerId())) {    // Yalnızca dosyayı oluşturan kişi silebilir.
            throw new StorageObjectAccessDeniedException();
        }

        StorageService storageService = this.storageServiceFactory.getStorageService(storageObject.getType());

        DeleteFileRequest deleteFileRequest = this.toDeleteFileRequest(this.mapper.toSearchResponse(storageObject));
        storageService.deleteFile(deleteFileRequest);

        this.repository.deleteById(id);
    }

    @Transactional
    public UploadProductImageResponse uploadProductImage(UploadProductImageRequest request) {
        final String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow();
        final String productImagesBucketName = this.configurationProperties.getAws().getS3().getProductImagesBucketName();
        final StorageService storageService = this.storageServiceFactory.getStorageService(StorageObjectType.S3);

        UploadFileRequest uploadFileRequest = new S3UploadFileRequest(request.multipartFile(), productImagesBucketName);
        UploadFileResponse uploadFileResponse = storageService.uploadFile(uploadFileRequest);

        final boolean privateAccess = false;

        StorageObjectCreateRequest storageObjectCreateRequest = new StorageObjectCreateRequest(
            uploadFileResponse.getFileName(),
            uploadFileResponse.getStorageName(),
            uploadFileResponse.getFileSize(),
            uploadFileResponse.getExtension(),
            currentUserId,
            StorageObjectType.S3,
            privateAccess
        );

        StorageObjectCreateResponse storageObjectCreateResponse = this.create(storageObjectCreateRequest);

        return this.mapper.toUploadProductImageResponse(storageObjectCreateResponse);
    }

    @Transactional
    public UploadCategoryImageResponse uploadCategoryImage(UploadCategoryImageRequest request) {
        final String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow();
        final String categoryBucketName = this.configurationProperties.getAws().getS3().getCategoryImagesBucketName();
        final StorageService storageService = this.storageServiceFactory.getStorageService(StorageObjectType.S3);

        UploadFileRequest uploadFileRequest = new S3UploadFileRequest(request.multipartFile(), categoryBucketName);
        UploadFileResponse uploadFileResponse = storageService.uploadFile(uploadFileRequest);

        final boolean privateAccess = false;

        StorageObjectCreateRequest storageObjectCreateRequest = new StorageObjectCreateRequest(
            uploadFileResponse.getFileName(),
            uploadFileResponse.getStorageName(),
            uploadFileResponse.getFileSize(),
            uploadFileResponse.getExtension(),
            currentUserId,
            StorageObjectType.S3,
            privateAccess
        );

        StorageObjectCreateResponse storageObjectCreateResponse = this.create(storageObjectCreateRequest);

        return this.mapper.toUploadCategoryImageResponse(storageObjectCreateResponse);
    }

    public MediaType getMediaType(String fileExtension) {
        switch (fileExtension) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
            case "webp":
                return MediaType.IMAGE_JPEG;
            default:
                throw new RuntimeException("Bilinmeyen medya türü !");
        }
    }

    private ReadFileRequest toReadFileRequest(StorageObjectSearchResponse storageObject) {
        if (storageObject == null) {
            throw new RuntimeException("Lütfen okumak istediğiniz dosyayı seçiniz.");
        }
        final String fileName = storageObject.fileName();
        final String storageName = storageObject.storageName();

        return switch (storageObject.type()) {
            case LOCAL -> new LocalStorageReadFileRequest(fileName, storageName);
            case S3 -> new S3ReadFileRequest(fileName, storageName);
        };
    }

    private DeleteFileRequest toDeleteFileRequest(StorageObjectSearchResponse storageObject) {
        if (storageObject == null) {
            throw new RuntimeException("Lütfen silmek istediğiniz dosyayı seçiniz.");
        }
        final String fileName = storageObject.fileName();
        final String storageName = storageObject.storageName();

        return switch (storageObject.type()) {
            case LOCAL -> new LocalStorageDeleteFileRequest(fileName, storageName);
            case S3 -> new S3DeleteFileRequest(fileName, storageName);
        };
    }
}

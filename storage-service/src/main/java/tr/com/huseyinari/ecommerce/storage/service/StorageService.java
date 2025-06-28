package tr.com.huseyinari.ecommerce.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.ecommerce.storage.domain.StorageObject;
import tr.com.huseyinari.ecommerce.storage.enums.StorageObjectType;
import tr.com.huseyinari.ecommerce.storage.exception.StorageObjectAccessDeniedException;
import tr.com.huseyinari.ecommerce.storage.exception.StorageObjectNotFoundException;
import tr.com.huseyinari.ecommerce.storage.mapper.StorageMapper;
import tr.com.huseyinari.ecommerce.storage.repository.StorageObjectRepository;
import tr.com.huseyinari.ecommerce.storage.request.*;
import tr.com.huseyinari.ecommerce.storage.response.*;
import tr.com.huseyinari.springweb.rest.RequestUtils;
import tr.com.huseyinari.utils.StringUtils;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageObjectRepository repository;
    private final LocalStorageService localStorageService;
    private final S3Service s3Service;

    @Value("${huseyinari.ecommerce.s3.buckets.product-images-bucket-name}")
    private String productImagesBucketName;

    @Transactional(readOnly = true)
    public StorageObjectSearchResponse findOne(Long id) {
        StorageObject storageObject = this.repository.findById(id).orElseThrow(StorageObjectNotFoundException::new);

        if (storageObject.isPrivateAccess()) {
            String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElse(null);

            if (!storageObject.getOwnerId().equals(currentUserId)) {
                throw new StorageObjectAccessDeniedException();
            }
        }

        return StorageMapper.toSearchResponse(storageObject);
    }

    @Transactional
    public StorageObjectCreateResponse create(StorageObjectCreateRequest request) {
        StorageObject storageObject = StorageMapper.toEntity(request);
        storageObject = this.repository.save(storageObject);

        return StorageMapper.toCreateResponse(storageObject);
    }

    @Transactional(readOnly = true)
    public FileContentResponse getFileContent(Long id) {
        StorageObjectSearchResponse storageObject = this.findOne(id);

        byte[] content = null;

        switch (storageObject.type()) {
            case S3 -> {
                S3GetFileRequest request = new S3GetFileRequest(storageObject.fileName(), storageObject.storageName());
                content = this.s3Service.getFileContent(request);
            } case LOCAL -> {
                LocalStorageGetFileRequest request = new LocalStorageGetFileRequest(storageObject.fileName(), storageObject.storageName());
                content = this.localStorageService.getFileContent(request);
            }
            default -> throw new RuntimeException("Dosya türüne ait depolama alanı bulunamadı !");
        }

        return new FileContentResponse(content, storageObject.fileName(), storageObject.extension());
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
    public UploadProductImageResponse uploadProductImage(UploadProductImageRequest request) {
        String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow();

        S3UploadRequest s3UploadRequest = new S3UploadRequest(request.multipartFile(), this.productImagesBucketName);
        S3UploadResponse s3UploadResponse = this.s3Service.uploadFile(s3UploadRequest);

        final boolean privateAccess = false;

        StorageObjectCreateRequest storageObjectCreateRequest = new StorageObjectCreateRequest(
                s3UploadResponse.fileName(),
                s3UploadResponse.bucketName(),
                s3UploadResponse.fileSize(),
                s3UploadResponse.extension(),
                currentUserId,
                StorageObjectType.S3,
                privateAccess
        );

        StorageObjectCreateResponse storageObjectCreateResponse = this.create(storageObjectCreateRequest);

        return StorageMapper.toUploadProductImageResponse(storageObjectCreateResponse);
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
}

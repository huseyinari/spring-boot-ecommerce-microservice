package tr.com.huseyinari.ecommerce.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.ecommerce.storage.domain.StorageObject;
import tr.com.huseyinari.ecommerce.storage.mapper.StorageMapper;
import tr.com.huseyinari.ecommerce.storage.repository.StorageObjectRepository;
import tr.com.huseyinari.ecommerce.storage.request.S3GetFileRequest;
import tr.com.huseyinari.ecommerce.storage.request.S3UploadRequest;
import tr.com.huseyinari.ecommerce.storage.request.UploadRequest;
import tr.com.huseyinari.ecommerce.storage.response.FileContentBase64Response;
import tr.com.huseyinari.ecommerce.storage.response.FileContentResponse;
import tr.com.huseyinari.ecommerce.storage.response.S3UploadResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadResponse;
import tr.com.huseyinari.springweb.rest.RequestUtils;
import tr.com.huseyinari.utils.StringUtils;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageObjectRepository repository;
    private final S3Service s3Service;

    public UploadResponse upload(UploadRequest request) {
        String currentUserId = null;

        if (request.privateAccess()) {
            currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow();
        }

        S3UploadRequest s3UploadRequest = new S3UploadRequest(request.multipartFile(), request.storageName());
        S3UploadResponse s3UploadResponse = s3Service.uploadFile(s3UploadRequest);

        StorageObject storageObject = new StorageObject();
        storageObject.setFileName(s3UploadResponse.fileName());
        storageObject.setFileSize(s3UploadResponse.fileSize());
        storageObject.setExtension(s3UploadResponse.extension());
        storageObject.setStorageName(s3UploadResponse.bucketName());
        storageObject.setPrivateAccess(request.privateAccess());
        storageObject.setOwnerId(currentUserId);

        repository.save(storageObject);

        return StorageMapper.toUploadResponse(storageObject);
    }

    public FileContentResponse getFileContent(Long storageObjectId) {
        StorageObject storageObject = repository.findById(storageObjectId).orElseThrow(() -> new RuntimeException("Dosya bulunamadı !"));

        if (storageObject.isPrivateAccess()) {
            String currentUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID).orElseThrow();

            if (!currentUserId.equals(storageObject.getOwnerId())) {
                throw new RuntimeException("Dosyaya erişim izniniz bulunmamaktadır !");
            }
        }

        S3GetFileRequest s3GetFileRequest = new S3GetFileRequest(storageObject.getFileName(), storageObject.getStorageName());
        byte[] content = s3Service.getFileContent(s3GetFileRequest);

        return new FileContentResponse(content, storageObject.getFileName(), storageObject.getExtension());
    }

    public FileContentBase64Response getFileContentBase64(Long storageObjectId) {
        final FileContentResponse fileContentResponse = this.getFileContent(storageObjectId);

        final String base64 = StringUtils.encodeBase64(fileContentResponse.content());
        final String fileName = fileContentResponse.fileName();
        final String extension = fileContentResponse.extension();

        return new FileContentBase64Response(base64, fileName, extension);
    }
}

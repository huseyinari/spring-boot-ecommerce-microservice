package tr.com.huseyinari.ecommerce.storage.service;

import tr.com.huseyinari.ecommerce.storage.request.ReadFileRequest;
import tr.com.huseyinari.ecommerce.storage.request.UploadFileRequest;
import tr.com.huseyinari.ecommerce.storage.response.ReadFileResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadFileResponse;

public interface StorageService {
    ReadFileResponse getFileContent(ReadFileRequest request);
    UploadFileResponse uploadFile(UploadFileRequest request);
}

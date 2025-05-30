package tr.com.huseyinari.ecommerce.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.com.huseyinari.ecommerce.storage.request.UploadRequest;
import tr.com.huseyinari.ecommerce.storage.response.FileContentBase64Response;
import tr.com.huseyinari.ecommerce.storage.response.FileContentResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadResponse;
import tr.com.huseyinari.ecommerce.storage.service.StorageService;
import tr.com.huseyinari.springweb.rest.IgnoreResponseBodyAdvice;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> upload(
        @RequestParam("file") MultipartFile multipartFile,
        @RequestParam("storage_name") String storageName,
        @RequestParam("private_access") boolean privateAccess
    ) {
        UploadRequest request = new UploadRequest(multipartFile, storageName, privateAccess);
        UploadResponse response = service.upload(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/base64/{storageObjectId}")
    public ResponseEntity<FileContentBase64Response> getBase64(@PathVariable Long storageObjectId) {
        FileContentBase64Response response = service.getFileContentBase64(storageObjectId);
        return ResponseEntity.ok(response);
    }

    @IgnoreResponseBodyAdvice
    @GetMapping("/download/{storageObjectId}")
    public ResponseEntity<byte[]> download(@PathVariable Long storageObjectId) {
        FileContentResponse response = service.getFileContent(storageObjectId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(response.content().length);
        headers.setContentDispositionFormData("attachment", response.fileName());

        return new ResponseEntity<>(response.content(), headers, HttpStatus.OK);
    }
}

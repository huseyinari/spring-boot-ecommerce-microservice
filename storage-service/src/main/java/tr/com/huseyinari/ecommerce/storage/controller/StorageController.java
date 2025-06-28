package tr.com.huseyinari.ecommerce.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.com.huseyinari.ecommerce.storage.request.UploadProductImageRequest;
import tr.com.huseyinari.ecommerce.storage.response.FileContentBase64Response;
import tr.com.huseyinari.ecommerce.storage.response.FileContentResponse;
import tr.com.huseyinari.ecommerce.storage.response.StorageObjectSearchResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadProductImageResponse;
import tr.com.huseyinari.ecommerce.storage.service.StorageService;
import tr.com.huseyinari.springweb.rest.IgnoreResponseBodyAdvice;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService service;

    @GetMapping("/{id}")
    public ResponseEntity<StorageObjectSearchResponse> findOne(@PathVariable Long id) {
        StorageObjectSearchResponse response = this.service.findOne(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/base64/{id}")
    public ResponseEntity<FileContentBase64Response> getBase64(@PathVariable Long id) {
        FileContentBase64Response response = this.service.getFileContentBase64(id);
        return ResponseEntity.ok(response);
    }

    @IgnoreResponseBodyAdvice
    @GetMapping("/content/{id}")
    public ResponseEntity<byte[]> getContent(@PathVariable Long id) {
        FileContentResponse response = this.service.getFileContent(id);
        MediaType mediaType = this.service.getMediaType(response.extension());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setCacheControl("no-cache, no-store, must-revalidate"); // opsiyonel
        headers.setPragma("no-cache");
        headers.setExpires(0);

        return new ResponseEntity<>(response.content(), headers, HttpStatus.OK);
    }

    @IgnoreResponseBodyAdvice
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        FileContentResponse response = this.service.getFileContent(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(response.content().length);
        headers.setContentDispositionFormData("attachment", response.fileName());

        return new ResponseEntity<>(response.content(), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/upload/product-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadProductImageResponse> uploadProductImage(
        @RequestParam("file") MultipartFile multipartFile
//        @RequestParam("private_access") boolean privateAccess
    ) {
        UploadProductImageRequest request = new UploadProductImageRequest(multipartFile);
        UploadProductImageResponse response = this.service.uploadProductImage(request);

        return ResponseEntity.ok(response);
    }
}

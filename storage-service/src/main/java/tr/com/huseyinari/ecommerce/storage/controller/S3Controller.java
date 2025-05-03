package tr.com.huseyinari.ecommerce.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.com.huseyinari.ecommerce.storage.service.S3Service;
import tr.com.huseyinari.springweb.rest.IgnoreResponseBodyAdvice;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile multipartFile) {
        s3Service.uploadFile(multipartFile);
        return ResponseEntity.ok().build();
    }

    @IgnoreResponseBodyAdvice
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) {
        byte[] fileContent = s3Service.getFileContent(fileName);

        if (fileContent == null) {
            throw new RuntimeException("Dosya bulunamadı !");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(fileContent.length);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}

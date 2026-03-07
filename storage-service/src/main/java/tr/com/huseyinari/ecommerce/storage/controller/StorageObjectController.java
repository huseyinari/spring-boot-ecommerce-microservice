package tr.com.huseyinari.ecommerce.storage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.com.huseyinari.ecommerce.storage.request.UploadCategoryImageRequest;
import tr.com.huseyinari.ecommerce.storage.request.UploadProductImageRequest;
import tr.com.huseyinari.ecommerce.storage.response.FileContentBase64Response;
import tr.com.huseyinari.ecommerce.storage.response.FileContentResponse;
import tr.com.huseyinari.ecommerce.storage.response.StorageObjectSearchResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadCategoryImageResponse;
import tr.com.huseyinari.ecommerce.storage.response.UploadProductImageResponse;
import tr.com.huseyinari.ecommerce.storage.service.StorageObjectService;
import tr.com.huseyinari.springweb.rest.IgnoreResponseBodyAdvice;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
@Tag(name = "Storage Controller", description = "Depolama Yönetimi")
public class StorageObjectController {
    private final StorageObjectService service;

    @Operation(
        summary = "Id'ye göre depolama nesnesi ara",
        description = "Belirtilen Id ile eşleşen depolama nesnesini getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Depolama nesnesi bulundu",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = StorageObjectSearchResponse.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<StorageObjectSearchResponse> findOne(@PathVariable Long id) {
        StorageObjectSearchResponse response = this.service.findOne(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Id'ye göre depolama nesnesinin içeriğini base64 getir.",
        description = "Belirtilen Id ile eşleşen depolama nesnesinin içeriğini base64 olarak getirir.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Depolama nesnesinin içeriği base64 olarak verildi.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = FileContentBase64Response.class)
            )
        )
    })
    @GetMapping("/base64/{id}")
    public ResponseEntity<FileContentBase64Response> getBase64(@PathVariable Long id) {
        FileContentBase64Response response = this.service.getFileContentBase64(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Id'ye göre depolama nesnesinin içeriğini ver",
        description = "Belirtilen Id ile eşleşen depolama nesnesinin içeriğini alır ve doğrudan tarayıcıda görüntüleyebilmek için gerekli olan response'u döndürür."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Depolama nesnesinin içeriği döndü. Dosyanın uzantısına göre farklı content'ler dönebilir !",
            content = {
                @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(type = "string", format = "binary")),
                @Content(mediaType = MediaType.APPLICATION_PDF_VALUE, schema = @Schema(type = "string", format = "binary")),
                @Content(mediaType = MediaType.IMAGE_JPEG_VALUE, schema = @Schema(type = "string", format = "binary")),
                @Content(mediaType = MediaType.IMAGE_PNG_VALUE, schema = @Schema(type = "string", format = "binary")),
                @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string", format = "binary"))
                // ... Burada dinamik bir içerik dönüyor aslında. Her şey olabilir.
            }
        )
    })
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

    @Operation(
        summary = "Id'ye göre depolama nesnesinin içeriğini indirme başlat",
        description = "Belirtilen Id ile eşleşen depolama nesnesinin içeriğini alır ve indirme olarak başlatır.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Depolama nesnesinin içeriğinin indirilme işlemi başlatıldı.",
            content = @Content(
                mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                schema = @Schema(type = "string", format = "binary")
            )
        )
    })
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

    @Operation(
        summary = "Bir depolama nesnesini tamamen sistemden siler.",
        description = "Servise gönderilen id'ye ait depolama nesnesini sistemden tamamen yok eder.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Bir ürün fotoğrafını depolama servisine kaydet",
        description = "Servise gönderilen resmi alır ve ürün fotoğraflarının bulunduğu depolama servisine kaydeder.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Ürün fotoğrafını depolama servisine yükleme işlemi başarılı.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = UploadProductImageResponse.class)
            )
        )
    })
    @PostMapping(value = "/upload/product-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadProductImageResponse> uploadProductImage(
        @RequestParam("file") MultipartFile multipartFile
    ) {
        UploadProductImageRequest request = new UploadProductImageRequest(multipartFile);
        UploadProductImageResponse response = this.service.uploadProductImage(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Bir kategori fotoğrafını depolama servisine kaydet",
        description = "Servise gönderilen resmi alır ve kategori fotoğraflarının bulunduğu depolama servisine kaydeder.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Kategori fotoğrafını depolama servisine yükleme işlemi başarılı.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = UploadCategoryImageResponse.class)
            )
        )
    })
    @PostMapping(value = "/upload/category-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadCategoryImageResponse> uploadCategoryImage(
        @RequestParam("file") MultipartFile multipartFile
    ) {
        UploadCategoryImageRequest request = new UploadCategoryImageRequest(multipartFile);
        UploadCategoryImageResponse response = this.service.uploadCategoryImage(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

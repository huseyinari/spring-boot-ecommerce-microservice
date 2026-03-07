package tr.com.huseyinari.ecommerce.category.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import tr.com.huseyinari.ecommerce.category.shared.response.UploadCategoryImageResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@FeignClient(value = "storage-service", path = "/api/v1/storage")
public interface StorageClient {
    @PostMapping(value = "/upload/category-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SinhaRestApiResponse<UploadCategoryImageResponse> uploadCategoryImage(@RequestPart("file") MultipartFile file);

    @DeleteMapping(value = "/{id}")
    void deleteFile(@PathVariable("id") Long id);
}

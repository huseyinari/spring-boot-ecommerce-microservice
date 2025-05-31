package tr.com.huseyinari.ecommerce.product.shared.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageObjectSearchResponse {
    private Long id;
    private String fileName;
    private String storageName;
    private Long fileSize;
    private String extension;
}

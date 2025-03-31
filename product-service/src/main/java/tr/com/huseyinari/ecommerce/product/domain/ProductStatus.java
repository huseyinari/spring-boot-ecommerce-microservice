package tr.com.huseyinari.ecommerce.product.domain;

import lombok.Getter;

@Getter
public enum ProductStatus {
    PENDING("PENDING", "Ürün oluşturulma aşamasında."),
    SUCCESS("SUCCESS", "Ürün başarıyla oluşturuldu."),
    FAILURE("FAILURE", "Ürün oluşturulurken hata oluştu.");

    private final String status;
    private final String description;

    ProductStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }
}

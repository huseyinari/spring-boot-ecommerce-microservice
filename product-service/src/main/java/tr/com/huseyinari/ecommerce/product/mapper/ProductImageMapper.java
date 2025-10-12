package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.ecommerce.product.response.ProductImageSearchResponse;
import tr.com.huseyinari.utils.StringUtils;

@Component
@RequiredArgsConstructor
public class ProductImageMapper {
    private final ECommerceConfigurationProperties configurationProperties;

    public ProductImageSearchResponse toSearchResponse(ProductImage productImage) {
        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        if (StringUtils.isBlank(storageObjectContentUrl)) {
            throw new RuntimeException("Ürün resmi için eksik bilgiler mevcut. Lütfen sistem yöneticiniz ile iletişime geçiniz.");
        }

        return new ProductImageSearchResponse(
            productImage.getId(),
            productImage.getProduct().getId(),
            productImage.getStorageObjectId(),
            storageObjectContentUrl + "/" + productImage.getStorageObjectId()
        );
    }
}

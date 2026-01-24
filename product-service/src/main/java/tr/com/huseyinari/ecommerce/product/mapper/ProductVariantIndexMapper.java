package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantIndex;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantIndexCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantIndexSearchPageableResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantIndexSearchResponse;
import tr.com.huseyinari.utils.CollectionUtils;
import tr.com.huseyinari.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductVariantIndexMapper {
    private final ECommerceConfigurationProperties configurationProperties;

    public ProductVariantIndexSearchResponse toSearchResponse(ProductVariantIndex productVariantIndex) {
        if (productVariantIndex == null) {
            return null;
        }

        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        if (StringUtils.isBlank(storageObjectContentUrl)) {
            throw new RuntimeException("Ürün resmi için eksik bilgiler mevcut. Lütfen sistem yöneticiniz ile iletişime geçiniz.");
        }

        String productId = productVariantIndex.getProduct() != null ? productVariantIndex.getProduct().getId() : null;
        String productName = productVariantIndex.getProduct() != null ? productVariantIndex.getProduct().getName() : null;

        final List<String> imageUrls = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(productVariantIndex.getImages())) {
            productVariantIndex.getImages()
                .stream()
                .map(ProductImage::getStorageObjectId)
                .forEach(storageObjectId -> imageUrls.add(storageObjectContentUrl + "/" + storageObjectId));
        }

        return new ProductVariantIndexSearchResponse(
            productVariantIndex.getId(),
            productId,
            productName,
            imageUrls,
            productVariantIndex.getPrice(),
            productVariantIndex.getDiscount(),
            productVariantIndex.getDiscountedPrice()
        );
    }

    public ProductVariantIndexCreateResponse toCreateResponse(ProductVariantIndex productVariantIndex) {
        if (productVariantIndex == null) {
            return null;
        }

        return new ProductVariantIndexCreateResponse(
            productVariantIndex.getId(),
            productVariantIndex.getVariantValueIndex(),
            productVariantIndex.getSkuCode(),
            productVariantIndex.getStock(),
            productVariantIndex.getPrice(),
            productVariantIndex.getDiscount(),
            productVariantIndex.getDiscountedPrice(),
            productVariantIndex.getQueryOrder(),
            productVariantIndex.getProduct().getId()
        );
    }

    public ProductVariantIndexSearchPageableResponse toSearchPageableResponse(Page<ProductVariantIndex> pageResult) {
        if (pageResult == null) {
            return null;
        }

        List<ProductVariantIndexSearchResponse> searchResponseList = pageResult
                .getContent()
                .stream()
                .map(this::toSearchResponse)
                .toList();

        ProductVariantIndexSearchPageableResponse response = new ProductVariantIndexSearchPageableResponse();
        response.setItems(searchResponseList);
        response.setPage(pageResult.getNumber());
        response.setSize(pageResult.getSize());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setFirst(pageResult.isFirst());
        response.setLast(pageResult.isLast());

        return response;
    }
}

package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchPageableResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;
import tr.com.huseyinari.utils.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ECommerceConfigurationProperties configurationProperties;

    public Product toEntity(ProductCreateRequest request) {
        if (request == null) {
            return null;
        }

        return Product.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .discount(request.discount())
                .categoryId(request.categoryId())
                .build();
    }

    public ProductSearchResponse toSearchResponse(Product product) {
        if (product == null) {
            return null;
        }

        final String storageObjectContentUrl = this.configurationProperties.getStorageObjectContentUrl();

        if (StringUtils.isBlank(storageObjectContentUrl)) {
            throw new RuntimeException("Ürün resmi için eksik bilgiler mevcut. Lütfen sistem yöneticiniz ile iletişime geçiniz.");
        }

        Set<String> imageUrls =
                product.getProductImages()
                        .stream()
                        .map(ProductImage::getStorageObjectId)
                        .map(storageObjectId -> storageObjectContentUrl + "/" + storageObjectId)
                        .collect(Collectors.toSet());

        return new ProductSearchResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSkuCode(),
                product.getPrice(),
                product.getDiscount(),
                product.getDiscountedPrice(),
                imageUrls
        );
    }
    public ProductCreateResponse toCreateResponse(Product product) {
        if (product == null) {
            return null;
        }

        Set<Long> imageStorageIds =
                product.getProductImages()
                    .stream()
                    .map(ProductImage::getStorageObjectId)
                    .collect(Collectors.toSet());

        ProductCreateResponse response = new ProductCreateResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setSkuCode(product.getSkuCode());
        response.setPrice(product.getPrice());
        response.setDiscount(product.getDiscount());
        response.setDiscountedPrice(product.getDiscountedPrice());
        response.setStatus(product.getStatus());
        response.setImageStorageIds(imageStorageIds);

        return response;
    }

    public ProductSearchPageableResponse toSearchPageableResponse(Page<Product> pageResult) {
        if (pageResult == null) {
            return null;
        }

        List<ProductSearchResponse> searchResponseList = pageResult
                .getContent()
                .stream()
                .map(this::toSearchResponse)
                .toList();

        ProductSearchPageableResponse response = new ProductSearchPageableResponse();
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

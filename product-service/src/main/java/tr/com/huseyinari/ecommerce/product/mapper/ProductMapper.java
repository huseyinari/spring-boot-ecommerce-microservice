package tr.com.huseyinari.ecommerce.product.mapper;

import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductMapper {
    private ProductMapper() {

    }
    public static Product toEntity(ProductCreateRequest request) {
        return Product.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .categoryId(request.categoryId())
                .build();
    }

    public static ProductSearchResponse toSearchResponse(Product product) {
        Set<String> imageUrls =
                product.getProductImages()
                        .stream()
                        .map(ProductImage::getImageUrl)
                        .collect(Collectors.toSet());

        return new ProductSearchResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSkuCode(),
                product.getPrice(),
                imageUrls
        );
    }
    public static ProductCreateResponse toCreateResponse(Product product) {
        Set<String> imageUrls =
                product.getProductImages()
                    .stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toSet());

        return new ProductCreateResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getSkuCode(),
            product.getPrice(),
            imageUrls
        );
    }
}

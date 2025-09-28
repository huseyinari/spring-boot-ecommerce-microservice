package tr.com.huseyinari.ecommerce.product.mapper;

import org.springframework.data.domain.Page;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductImage;
import tr.com.huseyinari.ecommerce.product.request.ProductCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchPageableResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductSearchResponse;

import java.math.BigDecimal;
import java.util.List;
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
                .discount(request.discount())
                .categoryId(request.categoryId())
                .build();
    }

    public static ProductSearchResponse toSearchResponse(Product product, String storageObjectContentUrl) {
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
    public static ProductCreateResponse toCreateResponse(Product product) {
        Set<Long> imageStorageIds =
                product.getProductImages()
                    .stream()
                    .map(ProductImage::getStorageObjectId)
                    .collect(Collectors.toSet());

        return new ProductCreateResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getSkuCode(),
            product.getPrice(),
            product.getDiscount(),
            product.getDiscountedPrice(),
            product.getStatus(),
            imageStorageIds
        );
    }

    public static ProductSearchPageableResponse toSearchPageableResponse(Page<Product> pageResult, String storageObjectContentUrl) {
        List<ProductSearchResponse> searchResponseList = pageResult
                .getContent()
                .stream()
                .map(product -> ProductMapper.toSearchResponse(product, storageObjectContentUrl))
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

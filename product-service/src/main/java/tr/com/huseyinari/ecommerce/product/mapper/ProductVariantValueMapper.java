package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariant;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantValue;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantValueCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantValueCreateResponse;

@Component
@RequiredArgsConstructor
public class ProductVariantValueMapper {
    public ProductVariantValue toEntity(ProductVariantValueCreateRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setId(request.productId());

        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(request.productVariantId());

        ProductVariantValue productVariantValue = new ProductVariantValue();
        productVariantValue.setProduct(product);
        productVariantValue.setProductVariant(productVariant);
        productVariantValue.setVariantValue(request.variantValue());

        return productVariantValue;
    }

    public ProductVariantValueCreateResponse toCreateResponse(ProductVariantValue productVariantValue) {
        if (productVariantValue == null) {
            return null;
        }

        return new ProductVariantValueCreateResponse(
            productVariantValue.getId(),
            productVariantValue.getProductVariant().getId(),
            productVariantValue.getProduct().getId(),
            productVariantValue.getVariantValue()
        );
    }
}

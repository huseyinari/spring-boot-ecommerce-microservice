package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariant;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantValue;
import tr.com.huseyinari.ecommerce.product.repository.JpaEntityResolver;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantValueCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantValueCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantValueSearchResponse;
import tr.com.huseyinari.utils.NumberUtils;
import tr.com.huseyinari.utils.StringUtils;

@Component
@RequiredArgsConstructor
public class ProductVariantValueMapper {
    private final ProductVariantMapper productVariantMapper;
    private final JpaEntityResolver jpaEntityResolver;

    public ProductVariantValue toEntity(ProductVariantValueCreateRequest request) {
        if (request == null) {
            return null;
        }

        Product product = null;
        if (StringUtils.isNotBlank(request.productId())) {
            product = this.jpaEntityResolver.getReference(Product.class, request.productId());
        }

        ProductVariant productVariant = null;
        if (NumberUtils.greaterZero(request.productVariantId())) {
            productVariant = this.jpaEntityResolver.getReference(ProductVariant.class, request.productVariantId());
        }

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

    public ProductVariantValueSearchResponse toSearchResponse(ProductVariantValue productVariantValue) {
        if (productVariantValue == null) {
            return null;
        }

        return new ProductVariantValueSearchResponse(
            productVariantValue.getId(),
            productVariantMapper.toSearchResponse(productVariantValue.getProductVariant()),
            productVariantValue.getProduct().getId(),
            productVariantValue.getVariantValue()
        );
    }
}

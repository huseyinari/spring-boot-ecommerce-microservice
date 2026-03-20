package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttribute;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttributeValue;
import tr.com.huseyinari.ecommerce.product.repository.JpaEntityResolver;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeValueCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeValueCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeValueSearchResponse;
import tr.com.huseyinari.utils.NumberUtils;
import tr.com.huseyinari.utils.StringUtils;

@Component
@RequiredArgsConstructor
public class ProductAttributeValueMapper {
    private final ProductAttributeMapper productAttributeMapper;
    private final JpaEntityResolver jpaEntityResolver;

    public ProductAttributeValue toEntity(ProductAttributeValueCreateRequest request) {
        if (request == null) {
            return null;
        }

        Product product = null;
        if (StringUtils.isNotBlank(request.productId())) {
            product = this.jpaEntityResolver.getReference(Product.class, request.productId());
        }

        ProductAttribute productAttribute = null;
        if (NumberUtils.greaterZero(request.productAttributeId())) {
            productAttribute = this.jpaEntityResolver.getReference(ProductAttribute.class, request.productAttributeId());
        }

        return ProductAttributeValue.builder()
                .product(product)
                .productAttribute(productAttribute)
                .attributeValue(request.attributeValue())
                .build();
    }

    public ProductAttributeValueCreateResponse toCreateResponse(ProductAttributeValue productAttributeValue) {
        if  (productAttributeValue == null) {
            return null;
        }

        return new ProductAttributeValueCreateResponse(
            productAttributeValue.getId(),
            productAttributeValue.getProductAttribute().getId(),
            productAttributeValue.getProduct().getId(),
            productAttributeValue.getAttributeValue()
        );
    }

    public ProductAttributeValueSearchResponse toSearchResponse(ProductAttributeValue productAttributeValue) {
        if (productAttributeValue == null) {
            return null;
        }

        return new ProductAttributeValueSearchResponse(
            productAttributeValue.getId(),
            productAttributeMapper.toSearchResponse(productAttributeValue.getProductAttribute()),
            productAttributeValue.getProduct().getId(),
            productAttributeValue.getAttributeValue()
        );
    }
}

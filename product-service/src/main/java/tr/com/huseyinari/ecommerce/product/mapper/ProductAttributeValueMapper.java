package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttribute;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttributeValue;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeValueCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeValueCreateResponse;
import tr.com.huseyinari.ecommerce.product.response.ProductAttributeValueSearchResponse;

@Component
@RequiredArgsConstructor
public class ProductAttributeValueMapper {
    private final ProductAttributeMapper productAttributeMapper;

    public ProductAttributeValue toEntity(ProductAttributeValueCreateRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setId(request.productId());

        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(request.productAttributeId());

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

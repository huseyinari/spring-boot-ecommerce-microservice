package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttribute;
import tr.com.huseyinari.ecommerce.product.domain.ProductAttributeValue;
import tr.com.huseyinari.ecommerce.product.request.ProductAttributeValueCreateRequest;

@Component
@RequiredArgsConstructor
public class ProductAttributeValueMapper {
    public ProductAttributeValue toEntity(ProductAttributeValueCreateRequest request) {
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
}

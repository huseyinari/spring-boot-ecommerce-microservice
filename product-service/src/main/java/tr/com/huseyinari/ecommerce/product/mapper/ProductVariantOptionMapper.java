package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariant;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantOption;
import tr.com.huseyinari.ecommerce.product.repository.JpaEntityResolver;
import tr.com.huseyinari.ecommerce.product.request.ProductVariantOptionCreateRequest;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantOptionCreateResponse;
import tr.com.huseyinari.utils.NumberUtils;

@Component
@RequiredArgsConstructor
public class ProductVariantOptionMapper {
    private final JpaEntityResolver jpaEntityResolver;

    public ProductVariantOption toEntity(ProductVariantOptionCreateRequest request) {
        if (request == null) {
            return null;
        }

        ProductVariant productVariant = null;
        if (NumberUtils.greaterZero(request.productVariantId())) {
            productVariant = this.jpaEntityResolver.getReference(ProductVariant.class, request.productVariantId());
        }

        ProductVariantOption productVariantOption = new ProductVariantOption();
        productVariantOption.setProductVariant(productVariant);
        productVariantOption.setOptionValue(request.optionValue());

        return productVariantOption;
    }

    public ProductVariantOptionCreateResponse toCreateResponse(ProductVariantOption productVariantOption) {
        if (productVariantOption == null) {
            return null;
        }

        return new ProductVariantOptionCreateResponse(
            productVariantOption.getId(),
            productVariantOption.getProductVariant().getId(),
            productVariantOption.getOptionValue()
        );
    }
}

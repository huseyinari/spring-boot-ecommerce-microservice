package tr.com.huseyinari.ecommerce.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.product.domain.ProductVariantIndex;
import tr.com.huseyinari.ecommerce.product.response.ProductVariantIndexCreateResponse;

@Component
@RequiredArgsConstructor
public class ProductVariantIndexMapper {
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
}

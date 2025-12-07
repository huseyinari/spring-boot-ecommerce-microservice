package tr.com.huseyinari.ecommerce.product.response;

import lombok.Getter;
import lombok.Setter;
import tr.com.huseyinari.ecommerce.product.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductCreateResponse {
    private String id;
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal discountedPrice;
    private ProductStatus status;
    private Set<Long> imageStorageIds;
    private List<ProductAttributeValueCreateResponse> attributeValues;
    private List<ProductVariantValueCreateResponse> variantValues;
}

package tr.com.huseyinari.ecommerce.product.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDetailSearchResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal discountedPrice;
    private List<String> imageUrls;
    private List<ProductAttributeValueSearchResponse> attributeValues;
    private List<ProductVariantValueSearchResponse> variantValues;
    private List<ProductVariantIndexSearchResponse> variantIndexes;
    private List<ProductReviewSearchResponse> reviews;
}

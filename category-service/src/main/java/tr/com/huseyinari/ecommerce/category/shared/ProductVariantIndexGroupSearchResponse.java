package tr.com.huseyinari.ecommerce.category.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantIndexGroupSearchResponse {
    private String queryName;
    private String queryValue;
    private Long total;
}

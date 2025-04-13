package tr.com.huseyinari.ecommerce.product.shared.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySearchResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Integer totalProductCount;
}

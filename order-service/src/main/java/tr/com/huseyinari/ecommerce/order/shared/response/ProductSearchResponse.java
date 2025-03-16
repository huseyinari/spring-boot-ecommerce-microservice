package tr.com.huseyinari.ecommerce.order.shared.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSearchResponse {
    private String id;
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal price;
}

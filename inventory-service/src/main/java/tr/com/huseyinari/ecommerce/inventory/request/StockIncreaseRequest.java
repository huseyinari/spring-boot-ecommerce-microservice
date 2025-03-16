package tr.com.huseyinari.ecommerce.inventory.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockIncreaseRequest {
    private String skuCode;
    private Integer quantity;
}

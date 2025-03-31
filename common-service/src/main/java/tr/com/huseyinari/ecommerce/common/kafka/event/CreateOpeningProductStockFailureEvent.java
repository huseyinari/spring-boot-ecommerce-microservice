package tr.com.huseyinari.ecommerce.common.kafka.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOpeningProductStockFailureEvent {
    private String skuCode;
    private String description;
}

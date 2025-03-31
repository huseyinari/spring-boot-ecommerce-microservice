package tr.com.huseyinari.ecommerce.common.kafka.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOpeningProductStockSuccessEvent {
    private String skuCode;
}

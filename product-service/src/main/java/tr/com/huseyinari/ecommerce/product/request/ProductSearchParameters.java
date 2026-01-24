package tr.com.huseyinari.ecommerce.product.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProductSearchParameters {
    private String name;
    private Long categoryId;
    Map<String, List<ProductSearchCompareValue>> params;

    @Getter
    @Setter
    public static class ProductSearchCompareValue {
        private BigDecimal min;
        private BigDecimal max;
        private Object value;
    }
}

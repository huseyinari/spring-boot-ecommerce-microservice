package tr.com.huseyinari.ecommerce.category.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductsFilterOptionSearchResponseValue {
    private String queryValue;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private Long total;
}
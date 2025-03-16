package tr.com.huseyinari.ecommerce.inventory.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockIncreaseResponse {
    private String skuCode;
    private Integer lastQuantity;   // Artırım işleminden sonra oluşan güncel stok sayısı
}

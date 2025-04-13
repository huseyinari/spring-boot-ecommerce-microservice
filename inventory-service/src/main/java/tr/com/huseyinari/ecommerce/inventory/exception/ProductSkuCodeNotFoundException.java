package tr.com.huseyinari.ecommerce.inventory.exception;

public class ProductSkuCodeNotFoundException extends RuntimeException {
    public ProductSkuCodeNotFoundException() {
        super("Ürün kodu ile eşleşen stok kaydı bulunamadı !");
    }
}

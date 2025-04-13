package tr.com.huseyinari.ecommerce.order.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Sipariş bilgilerinde belirtilen ürün sistemde bulunamadı");
    }
}

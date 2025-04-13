package tr.com.huseyinari.ecommerce.order.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String skuCode) {
        super("Sipariş için " + skuCode + " ürün stoğu yetersiz");
    }
}

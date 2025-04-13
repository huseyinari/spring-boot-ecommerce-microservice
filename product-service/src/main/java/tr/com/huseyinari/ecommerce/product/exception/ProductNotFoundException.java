package tr.com.huseyinari.ecommerce.product.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Ürün bulunamadı !");
    }
}

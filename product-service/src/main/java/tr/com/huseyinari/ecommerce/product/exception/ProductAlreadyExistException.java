package tr.com.huseyinari.ecommerce.product.exception;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException() {
        super("Ürün zaten mevcut !");
    }
}

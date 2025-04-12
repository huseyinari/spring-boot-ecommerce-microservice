package tr.com.huseyinari.ecommerce.category.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Kategori bulunamadı !");
    }
}

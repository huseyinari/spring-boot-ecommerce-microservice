package tr.com.huseyinari.ecommerce.category.exception;

public class CategoryProductsFilterOptionNotFoundException extends RuntimeException {
    public CategoryProductsFilterOptionNotFoundException() {
        super("Kategori ürün arama seçeneği bulunamadı.");
    }
}

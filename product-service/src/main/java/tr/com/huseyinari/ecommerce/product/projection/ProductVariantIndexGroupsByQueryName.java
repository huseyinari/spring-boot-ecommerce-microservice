package tr.com.huseyinari.ecommerce.product.projection;

public interface ProductVariantIndexGroupsByQueryName {
    String getQueryName();
    String getQueryValue();
    Long getTotal();
}
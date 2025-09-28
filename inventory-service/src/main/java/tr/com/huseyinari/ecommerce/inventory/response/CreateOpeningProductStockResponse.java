package tr.com.huseyinari.ecommerce.inventory.response;

public record CreateOpeningProductStockResponse(
    Long id,
    String skuCode,
    Integer quantity
) {}

package tr.com.huseyinari.ecommerce.order.request;

public record OrderCreateRequest(
    String skuCode,
    Integer quantity
) {}

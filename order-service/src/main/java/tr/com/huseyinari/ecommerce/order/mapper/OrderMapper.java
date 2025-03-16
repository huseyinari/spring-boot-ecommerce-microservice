package tr.com.huseyinari.ecommerce.order.mapper;

import tr.com.huseyinari.ecommerce.order.domain.Order;
import tr.com.huseyinari.ecommerce.order.request.OrderCreateRequest;
import tr.com.huseyinari.ecommerce.order.response.OrderCreateResponse;

import java.util.UUID;

public class OrderMapper {
    private OrderMapper() {

    }

    public static Order toEntity(OrderCreateRequest request) {
        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(request.skuCode())
                .quantity(request.quantity())
                .build();
    }

    public static OrderCreateResponse toCreateResponse(Order order) {
        return new OrderCreateResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getSkuCode(),
                order.getPrice(),
                order.getQuantity()
        );
    }
}

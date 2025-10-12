package tr.com.huseyinari.ecommerce.order.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.order.domain.Order;
import tr.com.huseyinari.ecommerce.order.request.OrderCreateRequest;
import tr.com.huseyinari.ecommerce.order.response.OrderCreateResponse;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    public Order toEntity(OrderCreateRequest request) {
        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(request.skuCode())
                .quantity(request.quantity())
                .build();
    }

    public OrderCreateResponse toCreateResponse(Order order) {
        return new OrderCreateResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getSkuCode(),
                order.getPrice(),
                order.getQuantity()
        );
    }
}

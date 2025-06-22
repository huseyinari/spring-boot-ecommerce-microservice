package tr.com.huseyinari.ecommerce.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.huseyinari.ecommerce.order.request.OrderCreateRequest;
import tr.com.huseyinari.ecommerce.order.response.OrderCreateResponse;
import tr.com.huseyinari.ecommerce.order.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderCreateResponse> placeOrder(@RequestBody OrderCreateRequest request) {
        OrderCreateResponse response = this.service.placeOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

package tr.com.huseyinari.ecommerce.order.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinari.ecommerce.order.client.InventoryClient;
import tr.com.huseyinari.ecommerce.order.client.ProductClient;
import tr.com.huseyinari.ecommerce.order.domain.Order;
import tr.com.huseyinari.ecommerce.order.exception.InsufficientStockException;
import tr.com.huseyinari.ecommerce.order.exception.ProductNotFoundException;
import tr.com.huseyinari.ecommerce.order.mapper.OrderMapper;
import tr.com.huseyinari.ecommerce.order.repository.OrderRepository;
import tr.com.huseyinari.ecommerce.order.request.OrderCreateRequest;
import tr.com.huseyinari.ecommerce.order.response.OrderCreateResponse;
import tr.com.huseyinari.ecommerce.order.shared.response.ProductSearchResponse;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final InventoryClient inventoryClient;
    private final ProductClient productClient;

    @Transactional
    public OrderCreateResponse placeOrder(OrderCreateRequest request) {
        SinhaRestApiResponse<ProductSearchResponse> productResponse = null;

        try {
            productResponse = this.productClient.get(request.skuCode());
        } catch (FeignException.NotFound e) {
            throw new ProductNotFoundException();
        }

        ProductSearchResponse product = productResponse.getData();

        SinhaRestApiResponse<Boolean> stockResponse = this.inventoryClient.isInStock(request.skuCode(), request.quantity());
        boolean isInStock = stockResponse.getData() != null && stockResponse.getData();

        if (isInStock) {
            Order order = this.mapper.toEntity(request);

            order.setPrice(product.getPrice());
            order = this.repository.save(order);

            logger.info("{} -> Sipariş başarıyla kaydedildi.", order.getId());

            // TODO: STOK DÜŞÜRÜLECEK
            return this.mapper.toCreateResponse(order);
        } else {
            throw new InsufficientStockException(request.skuCode());
        }
    }
}

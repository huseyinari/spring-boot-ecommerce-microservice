package tr.com.huseyinari.ecommerce.product.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockEvent;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.springweb.rest.RequestUtils;

@Component
@RequiredArgsConstructor
public class ProductKafkaProducer {
    @Value("${huseyinari.ecommerce.kafka.topics.create-opening-product-stock}")
    private String createOpeningProductStockTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void createOpeningProductStock(Product product) {
        String currentUsername = RequestUtils.getHeader("X-Authenticated-User-Name").orElseThrow();

        CreateOpeningProductStockEvent event = new CreateOpeningProductStockEvent();
        event.setSkuCode(product.getSkuCode());
        event.setCreatedBy(currentUsername);

        kafkaTemplate.send(createOpeningProductStockTopic, event);
    }
}

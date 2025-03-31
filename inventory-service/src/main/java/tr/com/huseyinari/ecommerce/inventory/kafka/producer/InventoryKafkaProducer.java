package tr.com.huseyinari.ecommerce.inventory.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockFailureEvent;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockSuccessEvent;

@Component
@RequiredArgsConstructor
public class InventoryKafkaProducer {
    @Value("${huseyinari.ecommerce.kafka.topics.create-opening-product-stock-success}")
    private String createOpeningProductStockSuccessTopic;

    @Value("${huseyinari.ecommerce.kafka.topics.create-opening-product-stock-failed}")
    private String createOpeningProductStockFailedTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOpeningProductStockSuccess(String skuCode) {
        CreateOpeningProductStockSuccessEvent event = new CreateOpeningProductStockSuccessEvent();
        event.setSkuCode(skuCode);

        kafkaTemplate.send(createOpeningProductStockSuccessTopic, event);
    }

    public void sendOpeningProductStockFailure(String skuCode, String errorDescription) {
        CreateOpeningProductStockFailureEvent event = new CreateOpeningProductStockFailureEvent();
        event.setSkuCode(skuCode);
        event.setDescription(errorDescription);

        kafkaTemplate.send(createOpeningProductStockFailedTopic, event);
    }
}

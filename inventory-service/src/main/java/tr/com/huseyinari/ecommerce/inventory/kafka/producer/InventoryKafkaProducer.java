package tr.com.huseyinari.ecommerce.inventory.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockFailureEvent;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockSuccessEvent;
import tr.com.huseyinari.ecommerce.inventory.config.ECommerceConfigurationProperties;

@Component
@RequiredArgsConstructor
public class InventoryKafkaProducer {
    private final ECommerceConfigurationProperties configurationProperties;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOpeningProductStockSuccess(String skuCode) {
        final String topicName = this.configurationProperties.getKafka().getTopics().getCreateOpeningProductStockSuccess();

        CreateOpeningProductStockSuccessEvent event = new CreateOpeningProductStockSuccessEvent();
        event.setSkuCode(skuCode);

        kafkaTemplate.send(topicName, event);
    }

    public void sendOpeningProductStockFailure(String skuCode, String errorDescription) {
        final String topicName = this.configurationProperties.getKafka().getTopics().getCreateOpeningProductStockFailed();

        CreateOpeningProductStockFailureEvent event = new CreateOpeningProductStockFailureEvent();
        event.setSkuCode(skuCode);
        event.setDescription(errorDescription);

        kafkaTemplate.send(topicName, event);
    }
}

package tr.com.huseyinari.ecommerce.product.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockEvent;
import tr.com.huseyinari.ecommerce.product.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.springweb.rest.RequestUtils;

@Component
@RequiredArgsConstructor
public class ProductKafkaProducer {
    private final ECommerceConfigurationProperties configurationProperties;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void createOpeningProductStock(Product product) {
        final String currentUsername = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_NAME).orElseThrow();
        final String topicName = this.configurationProperties.getKafka().getTopics().getCreateOpeningProductStock();

        CreateOpeningProductStockEvent event = new CreateOpeningProductStockEvent();
        event.setSkuCode(product.getSkuCode());
        event.setCreatedBy(currentUsername);

        this.kafkaTemplate.send(topicName, event);
    }
}

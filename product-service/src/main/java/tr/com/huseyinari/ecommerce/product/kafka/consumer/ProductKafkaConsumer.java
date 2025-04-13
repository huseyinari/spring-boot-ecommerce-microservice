package tr.com.huseyinari.ecommerce.product.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockFailureEvent;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockSuccessEvent;
import tr.com.huseyinari.ecommerce.product.domain.Product;
import tr.com.huseyinari.ecommerce.product.enums.ProductStatus;
import tr.com.huseyinari.ecommerce.product.repository.ProductRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductKafkaConsumer {
    Logger logger = LoggerFactory.getLogger(ProductKafkaConsumer.class);

    private final ProductRepository repository;

    @KafkaListener(
        topics = "${huseyinari.ecommerce.kafka.topics.create-opening-product-stock-failed}",
        groupId = "product-group"
    )
    public void handleCreateOpeningProductStockFailure(CreateOpeningProductStockFailureEvent message) {
        Optional<Product> optional = repository.findBySkuCode(message.getSkuCode());

        if (optional.isEmpty()) {
            logger.info("{} numaralı bir ürün olmadığı için işlem yapılmayacak.", message.getSkuCode());
            return;
        }

        Product product = optional.get();
        product.setStatus(ProductStatus.FAILURE);
        product.setFailureDescription(message.getDescription());

        logger.info("{} kodlu ürün için açılış stoğu oluşturulamadı. Hata ürüne işlendi: {}", message.getSkuCode(), message.getDescription());

        repository.save(product);
    }

    @KafkaListener(
        topics = "${huseyinari.ecommerce.kafka.topics.create-opening-product-stock-success}",
        groupId = "product-group"
    )
    public void handleCreateProductCompleted(CreateOpeningProductStockSuccessEvent message) {
        Optional<Product> optional = repository.findBySkuCode(message.getSkuCode());

        if (optional.isEmpty()) {
            logger.info("{} kodlu bir ürün olmadığı için işlem yapılmayacak.", message.getSkuCode());
            return;
        }

        Product product = optional.get();
        product.setStatus(ProductStatus.SUCCESS);

        logger.info("Ürünün açılış stoğu başarıyla oluşturuldu: {}", product.getSkuCode());

        repository.save(product);
    }
}

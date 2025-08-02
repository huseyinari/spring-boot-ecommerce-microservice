package tr.com.huseyinari.ecommerce.inventory.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.common.kafka.event.CreateOpeningProductStockEvent;
import tr.com.huseyinari.ecommerce.inventory.domain.Inventory;
import tr.com.huseyinari.ecommerce.inventory.kafka.producer.InventoryKafkaProducer;
import tr.com.huseyinari.ecommerce.inventory.repository.InventoryRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InventoryKafkaConsumer {
    Logger logger = LoggerFactory.getLogger(InventoryKafkaConsumer.class);

    private final InventoryRepository repository;
    private final InventoryKafkaProducer kafkaProducer;

    @KafkaListener(
        topics = "${huseyinari.ecommerce.kafka.topics.create-opening-product-stock}",
        groupId = "inventory-group"
    )
    public void handleOpeningProductStock(CreateOpeningProductStockEvent event) {
        final String skuCode = event.getSkuCode();

        try {
            Inventory newStock = new Inventory();
            newStock.setSkuCode(skuCode);
            newStock.setQuantity(0);

            // TODO: AUDITING BILGILERI NEREDE SETLENMELİ ???
            newStock.setCreatedBy(event.getCreatedBy());
            newStock.setCreatedDate(LocalDateTime.now());

            this.repository.save(newStock);

            logger.info("{} numaralı ürün için stok kaydı başarıyla açıldı. Mesaj ürün servisine gönderiliyor.", skuCode);

            this.kafkaProducer.sendOpeningProductStockSuccess(skuCode);
        } catch (Exception exception) {
            logger.info("{} numaralı ürün için stok kaydı açılışında hata oluştu. {}", skuCode, exception.getMessage());

            this.kafkaProducer.sendOpeningProductStockFailure(skuCode, exception.getMessage());
        }
    }
}

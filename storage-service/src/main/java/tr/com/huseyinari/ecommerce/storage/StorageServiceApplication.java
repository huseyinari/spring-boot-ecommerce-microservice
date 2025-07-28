package tr.com.huseyinari.ecommerce.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tr.com.huseyinari.ecommerce.storage.config.ECommerceConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ECommerceConfigurationProperties.class })
public class StorageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class);
    }
}

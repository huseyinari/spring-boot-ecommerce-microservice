package tr.com.huseyinari.ecommerce.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tr.com.huseyinari.ecommerce.inventory.config.ECommerceConfigurationProperties;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({ ECommerceConfigurationProperties.class })
public class InventoryServiceApplication {
	//TODO: https://www.geeksforgeeks.org/different-ways-to-establish-communication-between-spring-microservices/

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}

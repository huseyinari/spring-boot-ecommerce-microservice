package tr.com.huseyinari.ecommerce.category;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tr.com.huseyinari.ecommerce.category.config.ECommerceConfigurationProperties;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({ ECommerceConfigurationProperties.class })
public class CategoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryServiceApplication.class, args);
    }
}

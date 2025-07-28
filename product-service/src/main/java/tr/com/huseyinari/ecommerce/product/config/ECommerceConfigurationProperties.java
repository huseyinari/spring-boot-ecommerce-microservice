package tr.com.huseyinari.ecommerce.product.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "huseyinari.ecommerce")
@Getter
@Setter
public class ECommerceConfigurationProperties {
    private String baseUrl;
    private String storageObjectContentUrl;
}

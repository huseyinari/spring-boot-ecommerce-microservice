package tr.com.huseyinari.ecommerce.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import tr.com.huseyinari.ecommerce.common.config.BaseConfigurationProperties;

@ConfigurationProperties(prefix = "huseyinari.ecommerce")
@Getter
@Setter
public class ECommerceConfigurationProperties extends BaseConfigurationProperties {

}

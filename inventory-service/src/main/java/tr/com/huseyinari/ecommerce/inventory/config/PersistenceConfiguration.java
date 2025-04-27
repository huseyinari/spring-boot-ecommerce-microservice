package tr.com.huseyinari.ecommerce.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.springweb.rest.RequestUtils;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class PersistenceConfiguration {
    @Bean
    AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    static class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            return RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_NAME);
        }
    }
}
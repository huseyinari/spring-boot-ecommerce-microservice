package tr.com.huseyinari.ecommerce.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;

import java.util.Arrays;

@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public LoggingFilter() {
        super(LoggingFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            long startTime = System.currentTimeMillis();

            String authenticatedUsername = exchange.getRequest().getHeaders().getFirst(RequestHeaderConstants.AUTHENTICATED_USER_NAME);

            String path = exchange.getRequest().getPath().pathWithinApplication().value();

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        long duration = System.currentTimeMillis() - startTime;

                        // TODO: Authentication işlemi yapıldıktan sonra yeni bir istekle yeni bir akış başlatılıyormuş. Reactive mimarinin doğası gereği.
                        // CHATGPT şöyle diyor -> Reactive mimari yüzünden Authentication işlemi ayrı bir akış başlatıyor. Bu yüzden filter'lar 2 kez tetikleniyor: önce anonymous, sonra authenticated olarak.
                        // Normal MVC monolit sistemde (Blocking Servlet) böyle bir şey olmazdı. Ama Reactive Non-blocking dünyada her "durum değişimi" ayrı bir akış olarak ele alınıyor.

                        // Dolayısıyla bu log 2 kez basılıyor. Birisinde authenticatedusername dolu diğerinde boş. Boş olan zaten çok kısa sürüyor  1ms gibi.
                        // Bu kısım düzenlenmeli !! Çözüm bulunmalı !!
                        logger.info("---------------------------------");
                        logger.info("Request path: {}", path);
                        logger.info("Request user: {}", authenticatedUsername);
                        logger.info("Request Time: {} ms", duration);
                        logger.info("---------------------------------");
                    }));
        };
    }

    @Getter
    @Setter
    static class Config {

    }
}

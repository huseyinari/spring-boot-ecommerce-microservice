package tr.com.huseyinari.ecommerce.apigateway.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

// 1. YÖNTEM
//@Component
//@Order(Ordered.LOWEST_PRECEDENCE)   // Security işlemlerinden sonra çalışması için düşük order verdim
//public class MyAfterSecurityFilter implements WebFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        return ReactiveSecurityContextHolder.getContext()
//                .map(securityContext -> securityContext.getAuthentication())
//                .flatMap(authentication -> {
//                    System.out.println("Deneme");
//                    return chain.filter(exchange);
//                })
//                .switchIfEmpty(chain.filter(exchange));
//    }
//}



// Tanımladıktan sonra application.properties'de veya config server'da, filter'ın aktif olacağı route'lara filter tanımlaması yapılmalı.
@Component
public class MyAfterSecurityFilter extends AbstractGatewayFilterFactory<MyAfterSecurityFilter.Config> {
    public MyAfterSecurityFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return ReactiveSecurityContextHolder.getContext()
                    .map(securityContext -> securityContext.getAuthentication())
                    .flatMap(authentication -> {
                        System.out.println("Deneme");
                        return chain.filter(exchange);
                    })
                    .switchIfEmpty(chain.filter(exchange));
        };
    }

    @Getter
    @Setter
    @Builder
    public static class Config {
        private String headerName;
    }
}

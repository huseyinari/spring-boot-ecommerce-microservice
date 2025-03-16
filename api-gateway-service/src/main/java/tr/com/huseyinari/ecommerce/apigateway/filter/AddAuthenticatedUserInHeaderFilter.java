package tr.com.huseyinari.ecommerce.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.apigateway.security.CustomAuthenticationToken;

@Component
public class AddAuthenticatedUserInHeaderFilter extends AbstractGatewayFilterFactory<AddAuthenticatedUserInHeaderFilter.Config> {

    public AddAuthenticatedUserInHeaderFilter() {
        super(AddAuthenticatedUserInHeaderFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(AddAuthenticatedUserInHeaderFilter.Config config) {
        return (exchange, chain) -> {
            return ReactiveSecurityContextHolder.getContext()
                    .flatMap(securityContext -> {
                        CustomAuthenticationToken authenticationToken = (CustomAuthenticationToken) securityContext.getAuthentication();

                        if (authenticationToken == null)
                            return chain.filter(exchange);

                        String authenticatedUsername = authenticationToken.getUsername();
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-Authenticated-User-Name", authenticatedUsername)
                                .build();

                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    })
                    .switchIfEmpty(chain.filter(exchange));
        };
    }

    @Getter
    @Setter
    public static class Config {

    }
}

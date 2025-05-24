package tr.com.huseyinari.ecommerce.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.apigateway.security.CustomAuthenticationToken;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;

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

                        // Giriş yapan kullanıcıyı request header'a X-Authenticated-User-Name olarak ekleyecek...

                        String authenticatedUsername = authenticationToken.getUsername();
                        String authenticatedUserId = authenticationToken.getUserId();

                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header(RequestHeaderConstants.AUTHENTICATED_USER_NAME, authenticatedUsername)
                                .header(RequestHeaderConstants.AUTHENTICATED_USER_ID, authenticatedUserId)
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

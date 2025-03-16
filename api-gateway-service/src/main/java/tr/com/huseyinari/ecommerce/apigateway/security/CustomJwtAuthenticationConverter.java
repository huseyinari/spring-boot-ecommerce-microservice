package tr.com.huseyinari.ecommerce.apigateway.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    private final String RESOURCE_ACCESS_KEY = "resource_access";
    private final String ROLES_KEY = "roles";
    private final String CLIENT_ID_KEY = "ecommerce-client";


    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return Mono.fromSupplier(() -> {
            // Client rollerini al
            Collection<GrantedAuthority> authorities = this.extractAuthorities(jwt);

            // Kullanıcı bilgilerini al
            String username = jwt.getClaimAsString("preferred_username");
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("given_name");
            String familyName = jwt.getClaimAsString("family_name");

            return new CustomAuthenticationToken(username, name, familyName, email, authorities);   // <-- kendi oluşturduğumuz token sınıfı
        });
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaimAsMap(this.RESOURCE_ACCESS_KEY);

        if (resourceAccess == null || !resourceAccess.containsKey(this.CLIENT_ID_KEY)) {
            return Collections.emptyList();
        }

        Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get(this.CLIENT_ID_KEY);
        Collection<String> roles = (Collection<String>) clientRoles.get(this.ROLES_KEY);

        if (roles == null) {
            return Collections.emptyList();
        }

        return roles.stream()
                .map(role -> "ROLE_" + role.toUpperCase(Locale.ROOT))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

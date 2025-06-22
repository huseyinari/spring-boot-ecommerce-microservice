package tr.com.huseyinari.ecommerce.auth.service;

import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.auth.config.EcommerceKeycloakProperties;

@Component
@RequiredArgsConstructor
public class KeycloakAdminClientService {
    private final EcommerceKeycloakProperties keycloakProperties;

    private Keycloak keycloak = null;

    private Keycloak getInstance() {
        if (this.keycloak == null) {
            ResteasyClient resteasyClient = new ResteasyClientBuilder().connectionPoolSize(10).build();

            this.keycloak = KeycloakBuilder.builder()
                    .serverUrl(this.keycloakProperties.getServerUrl())
                    .realm(this.keycloakProperties.getRealm())
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(this.keycloakProperties.getAdminUsername())
                    .password(this.keycloakProperties.getAdminPassword())
                    .clientId(this.keycloakProperties.getClientId())
                    .clientSecret(this.keycloakProperties.getClientSecret())
                    .resteasyClient(resteasyClient)
                    .build();
        }

        return keycloak;
    }

    public UsersResource getUsersResource() {
        return this.getInstance().realm(this.keycloakProperties.getRealm()).users();
    }
}

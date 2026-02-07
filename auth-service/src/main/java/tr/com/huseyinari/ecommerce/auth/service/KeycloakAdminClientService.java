package tr.com.huseyinari.ecommerce.auth.service;

import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tr.com.huseyinari.ecommerce.auth.config.ECommerceConfigurationProperties;

@Component
@RequiredArgsConstructor
public class KeycloakAdminClientService {
    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminClientService.class);

    private final ECommerceConfigurationProperties configurationProperties;

    private Keycloak keycloak = null;

    private Keycloak getInstance() {
        if (this.keycloak == null) {
            ResteasyClient resteasyClient = new ResteasyClientBuilder().connectionPoolSize(10).build();

            this.keycloak = KeycloakBuilder.builder()
                    .serverUrl(this.configurationProperties.getKeycloak().getServerUrl())
                    .realm(this.configurationProperties.getKeycloak().getRealm())
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(this.configurationProperties.getKeycloak().getAdminUsername())
                    .password(this.configurationProperties.getKeycloak().getAdminPassword())
                    .clientId(this.configurationProperties.getKeycloak().getClientId())
                    .clientSecret(this.configurationProperties.getKeycloak().getClientSecret())
                    .resteasyClient(resteasyClient)
                    .build();
        }

        return keycloak;
    }

    public UsersResource getUsersResource() {
        return this.getInstance().realm(this.configurationProperties.getKeycloak().getRealm()).users();
    }
}

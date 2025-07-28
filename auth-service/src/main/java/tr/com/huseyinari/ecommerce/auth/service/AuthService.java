package tr.com.huseyinari.ecommerce.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinari.ecommerce.auth.config.ECommerceConfigurationProperties;
import tr.com.huseyinari.ecommerce.auth.exception.*;
import tr.com.huseyinari.ecommerce.auth.request.LoginRequest;
import tr.com.huseyinari.ecommerce.auth.request.RefreshTokenRequest;
import tr.com.huseyinari.ecommerce.auth.request.RegisterRequest;
import tr.com.huseyinari.ecommerce.auth.response.LoginResponse;
import tr.com.huseyinari.ecommerce.auth.response.RegisterResponse;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final KeycloakAdminClientService keycloakAdminClientService;
    private final ECommerceConfigurationProperties configurationProperties;

    public LoginResponse login(LoginRequest request) {
        String loginUrl = new StringBuilder()
                .append(this.configurationProperties.getKeycloak().getServerUrl())
                .append("/realms/")
                .append(this.configurationProperties.getKeycloak().getRealm())
                .append("/protocol/openid-connect/token")
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("scope", "openid");
        body.add("client_id", this.configurationProperties.getKeycloak().getClientId());
        body.add("client_secret", this.configurationProperties.getKeycloak().getClientSecret());
        body.add("username", request.username());
        body.add("password", request.password());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            String tokenType = rootNode.path("token_type").asText();
            String accessToken = rootNode.path("access_token").asText();
            String refreshToken = rootNode.path("refresh_token").asText();
            Integer expiresIn = rootNode.path("expires_in").asInt();
            Integer refreshExpiresIn = rootNode.path("refresh_expires_in").asInt();

            return new LoginResponse(tokenType, accessToken, refreshToken, expiresIn, refreshExpiresIn);
        } catch (HttpClientErrorException e) {
            if ("Unauthorized".equals(e.getStatusText())) {
                throw new BadCredentialsException();
            } else if ("Bad Request".equals(e.getStatusText())) {
                throw new UserNotActiveException();
            } else {
                throw new RuntimeException("Giriş yapma işlemi başarısız !");
            }
        } catch (Exception e) {
            throw new RuntimeException("Giriş yapma işlemi başarısız !");
        }
    }

    public LoginResponse refresh(RefreshTokenRequest request) {
        String loginUrl = new StringBuilder()
                .append(this.configurationProperties.getKeycloak().getServerUrl())
                .append("/realms/")
                .append(this.configurationProperties.getKeycloak().getRealm())
                .append("/protocol/openid-connect/token")
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("scope", "openid");
        body.add("client_id", this.configurationProperties.getKeycloak().getClientId());
        body.add("client_secret", this.configurationProperties.getKeycloak().getClientSecret());
        body.add("refresh_token", request.refreshToken());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            String tokenType = rootNode.path("token_type").asText();
            String accessToken = rootNode.path("access_token").asText();
            String refreshToken = rootNode.path("refresh_token").asText();
            Integer expiresIn = rootNode.path("expires_in").asInt();
            Integer refreshExpiresIn = rootNode.path("refresh_expires_in").asInt();

            return new LoginResponse(tokenType, accessToken, refreshToken, expiresIn, refreshExpiresIn);
        } catch (HttpClientErrorException e) {
            if ("Bad Request".equals(e.getStatusText())) {
                throw new InvalidRefreshTokenException();
            } else {
                throw new RuntimeException("Token yenileme işlemi sırasında hata oluştu !");
            }
        } catch (Exception e) {
            throw new RuntimeException("Token yenileme işlemi sırasında hata oluştu !");
        }
    }

    public RegisterResponse register(RegisterRequest request) {
        UsersResource usersResource = this.keycloakAdminClientService.getUsersResource();

        List<UserRepresentation> userRepresentationListByUsername = usersResource.search(request.userName());
        if (!userRepresentationListByUsername.isEmpty()) {
            throw new UsernameAlreadyExistException();
        }

        List<UserRepresentation> userRepresentationListByEmail = usersResource.search(null, null, null, request.email(), null, null);
        if (!userRepresentationListByEmail.isEmpty()) {
            throw new EmailAlreadyExistException();
        }

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.userName());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        Map<String, List<String>> attributes = new HashMap<>();

        String userId = UUID.randomUUID().toString();
        attributes.put("userId", Collections.singletonList(userId));    // Kullanıcıya attribute olarak kendi oluşturduğum bir ID veriyorum.

        user.setAttributes(attributes);

        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            return new RegisterResponse(userId, user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
        } else {
            throw new RuntimeException("Kullanıcı kaydetme işleminde bir hata oluştu !");
        }
    }
}

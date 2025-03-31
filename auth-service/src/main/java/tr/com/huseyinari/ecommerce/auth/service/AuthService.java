package tr.com.huseyinari.ecommerce.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinari.ecommerce.auth.config.EcommerceKeycloakProperties;
import tr.com.huseyinari.ecommerce.auth.request.LoginRequest;
import tr.com.huseyinari.ecommerce.auth.request.RegisterRequest;
import tr.com.huseyinari.ecommerce.auth.response.LoginResponse;
import tr.com.huseyinari.ecommerce.auth.response.RegisterResponse;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final KeycloakAdminClientService keycloakAdminClientService;
    private final EcommerceKeycloakProperties keycloakProperties;

    public LoginResponse login(LoginRequest request) {
        String loginUrl = new StringBuilder()
                .append(keycloakProperties.getServerUrl())
                .append("/realms/")
                .append(keycloakProperties.getRealm())
                .append("/protocol/openid-connect/token")
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("scope", "openid");
        body.add("client_id", keycloakProperties.getClientId());
        body.add("client_secret", keycloakProperties.getClientSecret());
        body.add("username", request.username());
        body.add("password", request.password());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            String accessToken = rootNode.path("access_token").asText();
            String refreshToken = rootNode.path("refresh_token").asText();

            return new LoginResponse(accessToken, refreshToken);
        } catch (HttpClientErrorException e) {
            if ("Unauthorized".equals(e.getStatusText())) {
                throw new RuntimeException("Kullanıcı adı veya şifre yanlış !");
            } else if ("Bad Request".equals(e.getStatusText())) {
                throw new RuntimeException("Kullanıcı hesabı aktif değil !");
            } else {
                throw new RuntimeException("Giriş yapma işlemi başarısız !");
            }
        } catch (Exception e) {
            throw new RuntimeException("Giriş yapma işlemi başarısız !");
        }
    }

    public RegisterResponse register(RegisterRequest request) {
        UsersResource usersResource = this.keycloakAdminClientService.getUsersResource();

        List<UserRepresentation> userRepresentationListByUsername = usersResource.search(request.userName()); // true = tam eşleşme için
        if (!userRepresentationListByUsername.isEmpty()) {
            throw new RuntimeException("Hesap zaten kullanılıyor.");
        }

        List<UserRepresentation> userRepresentationListByEmail = usersResource.search(null, null, null, request.email(), null, null);
        if (!userRepresentationListByEmail.isEmpty()) {
            throw new RuntimeException("E-Posta adresi kullanılıyor.");
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

        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            return new RegisterResponse(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
        } else {
            throw new RuntimeException("Kullanıcı kaydetme işleminde bir hata oluştu !");
        }
    }
}

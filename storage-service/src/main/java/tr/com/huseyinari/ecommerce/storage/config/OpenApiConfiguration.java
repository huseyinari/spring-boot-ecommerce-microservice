package tr.com.huseyinari.ecommerce.storage.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@OpenAPIDefinition
@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development Server");

        Contact contact = new Contact();
        contact.setName("Hüseyin ARI");
        contact.setEmail("hsynari1060@gmail.com");

        Info info = new Info()
            .title("Storage Service API")
            .version("1.0.0")
            .description("Depolama yönetimi ile ilgili tüm servisleri içerir.")
            .contact(contact);

// --------------------- JWT LOGİN SERVİS ---------------------
        SecurityScheme securitySchemeBearer = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("JWT token'ınızı buraya giriniz.");

        // Components tanımı
        Components components = new Components()
            .addSecuritySchemes("bearerAuth", securitySchemeBearer);

        PathItem loginPath = new PathItem()
            .post(
                new Operation()
                    .tags(Arrays.asList("Authentication"))
                    .summary("Kullanıcı girişi yapar")
                    .description("Username ve password ile giriş yaparak JWT token alır")
                    .security(Collections.emptyList())
                    .requestBody(
                        new RequestBody()
                            .required(true)
                            .content(
                                new Content()
                                    .addMediaType(
                                        "application/json",
                                        new MediaType()
                                            .schema(
                                                new Schema()
                                                    .type("object")
                                                    .addProperties("username", new Schema<>().type("string").example("username"))
                                                    .addProperties("password", new Schema<>().type("string").example("password123"))
                                            )
                                    )
                            )
                    )
                    .responses(new ApiResponses()
                        .addApiResponse("200", new ApiResponse()
                            .description("Giriş başarılı !")
                            .content(new Content()
                                .addMediaType("application/json",
                                    new MediaType().schema(new Schema<>()
                                        .type("object")
                                        .addProperties("accessToken", new Schema<>().type("string").example("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."))))))
                        .addApiResponse("401", new ApiResponse().description("Kullanıcı adı veya şifre hatalı !"))));

        Paths paths = new Paths();
        paths.addPathItem("/api/v1/auth/login", loginPath);

        return new OpenAPI()
            .info(info)
            .addServersItem(server)
            .components(components)
            .paths(paths);
    }
}
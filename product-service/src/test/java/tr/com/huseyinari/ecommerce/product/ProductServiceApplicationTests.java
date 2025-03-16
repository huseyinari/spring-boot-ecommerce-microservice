package tr.com.huseyinari.ecommerce.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import tr.com.huseyinari.ecommerce.product.response.ProductCreateResponse;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")       <--- PROFİLE TEST OLUYOR FAKAT data-test.sql gibi dosyaları @DynamicPropertySource'ta belirtmeyince çalıştırmıyor
class ProductServiceApplicationTests {
    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine3.21");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.sql.init.mode", () -> "always");
        registry.add("spring.sql.init.schema-locations", () -> "classpath:db-scripts/schema.sql");
        registry.add("spring.sql.init.data-locations", () -> "classpath:db-scripts/data-test.sql");
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void testFindAll() {
        ResponseEntity<List<ProductCreateResponse>> responseList = restTemplate.exchange(
                "/api/v1/product",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductCreateResponse>>() {}
        );
        System.out.println("responseList: ");
        for (ProductCreateResponse response : responseList.getBody()) {
            System.out.println(response);
        }

        ProductCreateResponse firstProduct = responseList.getBody().getFirst();
        assertEquals("Iphone 14", firstProduct.name());
        assertEquals("Iphone 14 Orjinal Türkiye Garantili", firstProduct.description());
        assertEquals("iphone_14", firstProduct.skuCode());
        assertEquals(0, BigDecimal.valueOf(50000.00).compareTo(firstProduct.price()));
    }

    @Test
    void testCreate() {
        String requestBody = """
                    {
                        "name": "Yeni Ürün",
                        "description": "Yeni Ürün Açıklaması",
                        "price": 50000
                    }
                """;

        RestAssured.given()
                .contentType("application/json")
                .when()
                .body(requestBody)
                .post("/api/v1/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Yeni Ürün"))
                .body("description", Matchers.equalTo("Yeni Ürün Açıklaması"))
                .body("price", Matchers.equalTo(50000));
    }
}

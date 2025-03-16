package tr.com.huseyinari.ecommerce.order;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import tr.com.huseyinari.ecommerce.order.client.InventoryClient;
import tr.com.huseyinari.ecommerce.order.stubs.InventoryClientStub;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")       <--- PROFİLE TEST OLUYOR FAKAT data-test.sql gibi dosyaları @DynamicPropertySource'ta belirtmeyince çalıştırmıyor
@AutoConfigureWireMock(port = 0)		// <---- Wiremock servisi konfigüre et (port 0 = rastgele müsait bir port)
class OrderServiceApplicationTests {
	@LocalServerPort
	private Integer port;

	// restTemplate yerine RestAssured kullandık
//	@Autowired
//	private TestRestTemplate restTemplate;

//	@MockBean, @SpyBean	 <-- Spring Boot 3.4.0 sonrası Deprecate olmuş
//	@MockitoBean	// <--- @MockBean yerine MockitoBean, @SpyBean yerine @MockitoSpyBean kullanabiliriz
//	private InventoryClient inventoryClient;

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
	void testSuccessCreate() {
//		Mockito.when(inventoryClient.isInStock("iphone_14", 1)).thenReturn(true);

		InventoryClientStub.stubInventoryCall("iphone_14", 1);

		String requestBody = """
			{
				"skuCode": "iphone_14",
				"price": 50000,
				"quantity": 1
			}
		""";

		RestAssured.given()
				.contentType("application/json")
				.when()
				.body(requestBody)
				.post("/api/v1/order")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("orderNumber", Matchers.notNullValue())
				.body("skuCode", Matchers.equalTo("iphone_14"))
				.body("price", Matchers.equalTo(50000))
				.body("quantity", Matchers.equalTo(1));
	}

//	@Test
//	void testCreateWithoutStock() {
////		Mockito.when(inventoryClient.isInStock("iphone_14", 999)).thenReturn(false);
//
//		String requestBody = """
//			{
//				"skuCode": "iphone_14",
//				"price": 50000,
//				"quantity": 999
//			}
//		""";
//
//		RestAssured.given()
//				.contentType("application/json")
//				.when()
//				.body(requestBody)
//				.post("/api/v1/order")
//				.then()
//				.statusCode(500);
//	}
}


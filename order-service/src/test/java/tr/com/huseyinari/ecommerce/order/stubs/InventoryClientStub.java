package tr.com.huseyinari.ecommerce.order.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {
    // Wiremock api çağrımlarının sonuçları
    public static void stubInventoryCall(String skuCode, Integer quantity) {
        stubFor(
            get(urlEqualTo("/api/v1/inventory/is-in-stock?skuCode=" + skuCode + "&quantity=" + quantity))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("true"))
        );
    }
}

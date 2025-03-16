package tr.com.huseyinari.ecommerce.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tr.com.huseyinari.ecommerce.apigateway.security.CustomAuthenticationToken;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}

//	@RestController
//	class TestController {
//		@GetMapping("/test")
//		public Mono<ResponseEntity<Void>> test(@AuthenticationPrincipal CustomAuthenticationToken authenticationToken) {
//			System.out.println("authenticationToken: " + authenticationToken);
//
//			ReactiveSecurityContextHolder.getContext()
//					.doOnNext(securityContext -> {
//						Authentication authentication = securityContext.getAuthentication();
//						if (authentication == null) {
//							System.out.println("Authentication nesnesi null!");
//						} else {
//							System.out.println("Kullanıcı bulundu: " + authentication.getName());
//						}
//					})
//					.switchIfEmpty(Mono.fromRunnable(() -> System.out.println("SecurityContext boş!")))
//					.then();
//
//			return Mono.just(ResponseEntity.ok().build());
//		}
//	}
}

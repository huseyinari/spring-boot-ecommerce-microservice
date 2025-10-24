package tr.com.huseyinari.ecommerce.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import tr.com.huseyinari.ecommerce.apigateway.security.CustomJwtAuthenticationConverter;

import java.util.List;

@Configuration
//@EnableWebSecurity    // <--- REAKTİF OLMASAYDI KULLANABİLİRDİK
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2ResourceServerProperties properties;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        final String[] permittedUrls = {
            "/public/**",
            "/eureka/**",
            "/actuator/**",
            "/swagger-ui/**", "/v3/api-docs/**",
            "/product/swagger/**", "/product/swagger-ui/**",
            "/category/swagger/**", "/category/swagger-ui/**",
            "/inventory/swagger/**", "/inventory/swagger-ui/**",
            "/order/swagger/**", "/order/swagger-ui/**",
            "/storage/swagger/**", "/storage/swagger-ui/**",
            "/auth/swagger/**", "/auth/swagger-ui/**",
            "/api/v1/auth/**",
            "/api/v1/storage/content/{id}",
            "/api/v1/category/menu",
            "/api/v1/category/popular",
            "/api/v1/carousel-item/{carouselName}",
            "/api/v1/product/most-inspected/today",
            "/api/v1/product/search",
            "/api/v1/category/products-filter-option/{categoryId}"
        };

        http
            .authorizeExchange(auth -> auth
                .pathMatchers(permittedUrls).permitAll()
                .pathMatchers(HttpMethod.POST, "/api/v1/product").hasRole("CREATE_PRODUCT")
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                jwtConfig -> jwtConfig
                        .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter())
                        .jwtDecoder(ReactiveJwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri()))
            ));

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
//        http.cors(ServerHttpSecurity.CorsSpec::disable);

        return http.build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.addAllowedOrigin("http://localhost:3000"); // React app URL
        corsConfig.addAllowedOriginPattern("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

    // REAKTİK OLMASAYDI KULLANABİLİRDİK
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("eureka", "/eureka/**", "/actuator/**").permitAll()
//                        .anyRequest().authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .build();
//    }
//

}

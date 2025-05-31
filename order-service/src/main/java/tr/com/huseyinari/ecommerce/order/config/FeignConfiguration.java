package tr.com.huseyinari.ecommerce.order.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.huseyinari.ecommerce.common.constants.RequestHeaderConstants;
import tr.com.huseyinari.springweb.rest.RequestUtils;

import java.util.Optional;

@Configuration
public class FeignConfiguration {
    @Bean
    public RequestInterceptor addAuthenticatedUserInRequestInterceptor() {
        return requestTemplate -> {
            // Api Gateway'den gelen giriş yapmış kullanıcı bilgilerini içerdeki servisleri çağırırken de iletecek.
            Optional<String> optionalUsername = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_NAME);
            Optional<String> optionalUserId = RequestUtils.getHeader(RequestHeaderConstants.AUTHENTICATED_USER_ID);

            if (optionalUsername.isPresent()) {
                String authenticatedUsername = optionalUsername.get();
                requestTemplate.header(RequestHeaderConstants.AUTHENTICATED_USER_NAME, authenticatedUsername);
            }
            if (optionalUserId.isPresent()) {
                String authenticatedUserId = optionalUserId.get();
                requestTemplate.header(RequestHeaderConstants.AUTHENTICATED_USER_ID, authenticatedUserId);
            }
        };
    }

// Interceptor'ı ayrı bir sınıf olarak da yazabilirdik.

//    @Component
//    class FeignRequestInterceptor implements RequestInterceptor {
//        @Override
//        public void apply(RequestTemplate requestTemplate) {
//            // Api Gateway'den gelen giriş yapmış kullanıcı adını içerdeki servisleri çağırırken de iletecek.
//            Optional<String> optionalUsername = RequestUtils.getHeader("X-Authenticated-User-Name");
//
//            if (optionalUsername.isPresent()) {
//                String authenticatedUsername = optionalUsername.get();
//                requestTemplate.header("X-Authenticated-User-Name", authenticatedUsername);
//            }
//        }
//    }
}

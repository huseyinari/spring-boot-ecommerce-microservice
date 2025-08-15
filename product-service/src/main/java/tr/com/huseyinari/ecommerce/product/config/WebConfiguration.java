package tr.com.huseyinari.ecommerce.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

        pageableResolver.setOneIndexedParameters(false); // True ayarlarsak, client page değerini 1'den başlayarak gönderir. Spring MVC -1 işlemini yapar
        pageableResolver.setMaxPageSize(500);   // max page limiti
        pageableResolver.setFallbackPageable(
            PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdDate"))
        );

        resolvers.add(pageableResolver);
    }
}

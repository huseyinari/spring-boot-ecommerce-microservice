package tr.com.huseyinari.ecommerce.storage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.CacheControl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/storage/**")
                .addResourceLocations("classpath:/public/")
                .setCacheControl(CacheControl.maxAge(Duration.ofHours(1)));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

        pageableResolver.setOneIndexedParameters(false);
        pageableResolver.setMaxPageSize(500);
        pageableResolver.setFallbackPageable(
                PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdDate"))
        );

        resolvers.add(pageableResolver);
    }
}
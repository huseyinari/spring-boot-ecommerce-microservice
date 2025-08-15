package tr.com.huseyinari.ecommerce.category.config;

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

        pageableResolver.setOneIndexedParameters(false);
        pageableResolver.setMaxPageSize(500);
        pageableResolver.setFallbackPageable(
                PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdDate"))
        );

        resolvers.add(pageableResolver);
    }
}

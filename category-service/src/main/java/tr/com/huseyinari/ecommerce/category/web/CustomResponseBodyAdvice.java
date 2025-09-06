package tr.com.huseyinari.ecommerce.category.web;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.huseyinari.springweb.rest.SinhaResponseBodyAdvice;

@RestControllerAdvice
public class CustomResponseBodyAdvice extends SinhaResponseBodyAdvice {
    @Override
    public String[] getIgnoreUrls() {
        return new String[] {
            "/actuator/**",
            "/category/swagger/**"
        };
    }
}

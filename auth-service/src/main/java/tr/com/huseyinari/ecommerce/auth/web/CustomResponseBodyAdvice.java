package tr.com.huseyinari.ecommerce.auth.web;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.huseyinari.springweb.rest.SinhaResponseBodyAdvice;

@RestControllerAdvice
public class CustomResponseBodyAdvice extends SinhaResponseBodyAdvice {
}

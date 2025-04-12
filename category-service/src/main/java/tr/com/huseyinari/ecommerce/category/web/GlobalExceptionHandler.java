package tr.com.huseyinari.ecommerce.category.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.huseyinari.ecommerce.category.exception.CategoryNotFoundException;
import tr.com.huseyinari.springweb.rest.SinhaGlobalExceptionHandler;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends SinhaGlobalExceptionHandler {
    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        final Map<String, String> errors = new HashMap<>();
        errors.put("system", exception.getMessage());

        SinhaRestApiResponse<Object> response = new SinhaRestApiResponse<>();
        response.setData(null);
        response.setResponseTime(LocalDateTime.now());
        response.setErrors(errors);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

package tr.com.huseyinari.ecommerce.inventory.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.huseyinari.ecommerce.inventory.exception.ProductSkuCodeNotFoundException;
import tr.com.huseyinari.springweb.rest.SinhaGlobalExceptionHandler;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends SinhaGlobalExceptionHandler {
    @ExceptionHandler({ProductSkuCodeNotFoundException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleProductSkuCodeNotFoundException(ProductSkuCodeNotFoundException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

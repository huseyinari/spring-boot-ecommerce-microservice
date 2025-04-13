package tr.com.huseyinari.ecommerce.product.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.huseyinari.ecommerce.product.exception.ProductAlreadyExistException;
import tr.com.huseyinari.ecommerce.product.exception.ProductNotFoundException;
import tr.com.huseyinari.springweb.rest.SinhaGlobalExceptionHandler;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends SinhaGlobalExceptionHandler {
    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleProductNotFoundException(ProductNotFoundException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProductAlreadyExistException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleProductAlreadyExistException(ProductAlreadyExistException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

package tr.com.huseyinari.ecommerce.order.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.huseyinari.ecommerce.order.exception.InsufficientStockException;
import tr.com.huseyinari.ecommerce.order.exception.ProductNotFoundException;
import tr.com.huseyinari.springweb.rest.SinhaGlobalExceptionHandler;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends SinhaGlobalExceptionHandler {
    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleProductNotFoundException(ProductNotFoundException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InsufficientStockException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleInsufficientStockException(InsufficientStockException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

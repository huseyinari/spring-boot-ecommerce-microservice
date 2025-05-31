package tr.com.huseyinari.ecommerce.storage.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.huseyinari.ecommerce.storage.exception.StorageObjectNotFoundException;
import tr.com.huseyinari.springweb.rest.SinhaGlobalExceptionHandler;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends SinhaGlobalExceptionHandler {
    @ExceptionHandler(StorageObjectNotFoundException.class)
    public ResponseEntity<SinhaRestApiResponse<Object>> handleStorageObjectNotFoundException(StorageObjectNotFoundException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

package tr.com.huseyinari.ecommerce.auth.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.huseyinari.ecommerce.auth.exception.*;
import tr.com.huseyinari.springweb.rest.SinhaGlobalExceptionHandler;
import tr.com.huseyinari.springweb.rest.SinhaRestApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends SinhaGlobalExceptionHandler {
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleBadCredentialsException(BadCredentialsException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserNotActiveException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleUserNotActiveException(UserNotActiveException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({InvalidRefreshTokenException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleInvalidRefreshTokenException(InvalidRefreshTokenException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameAlreadyExistException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleUsernameAlreadyExistException(UsernameAlreadyExistException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmailAlreadyExistException.class})
    public ResponseEntity<SinhaRestApiResponse<Object>> handleEmailAlreadyExistException(EmailAlreadyExistException exception) {
        return super.fromSingleErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

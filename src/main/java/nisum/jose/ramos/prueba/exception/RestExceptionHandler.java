package nisum.jose.ramos.prueba.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class RestExceptionHandler  {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        logger.info("here!!");
        List<String> message = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        return new ResponseEntity<>(getException(message), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    private List<GlobalException> getException(List<String> errors) {
       return errors.stream().map(e->{
            GlobalException GE = new GlobalException(e);
            return GE;
        }).toList();
    }

    private Map<String, List<String>> getErrors(List<String> errors) {

        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("message", errors);
        return errorResponse;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleValidationErrors(ResponseStatusException ex) {
        return new ResponseEntity<>(GlobalException.builder()
                .message(ex.getReason())
                .build(), ex.getStatusCode());
    }

}

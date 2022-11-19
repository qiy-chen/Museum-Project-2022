package ca.mcgill.ecse321.MuseumBackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ArtworkExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ArtworkException.class)
    public ResponseEntity<String> handleEventRegistrationException(ArtworkException ex) {
        return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
    }
    
}
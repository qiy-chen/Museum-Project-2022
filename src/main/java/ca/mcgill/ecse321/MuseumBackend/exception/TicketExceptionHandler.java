package ca.mcgill.ecse321.MuseumBackend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TicketExceptionHandler{
  @ExceptionHandler(TicketException.class)
  public ResponseEntity<String> handleEventRegistrationException(TicketException ex) {
      return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
  }
}

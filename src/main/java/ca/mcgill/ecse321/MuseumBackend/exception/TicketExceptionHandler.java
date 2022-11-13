package ca.mcgill.ecse321.MuseumBackend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TicketExceptionHandler extends ResponseEntityExceptionHandler{
  @ExceptionHandler(TicketException.class)
  public ResponseEntity<String> handleTicketException (TicketException ex) {
      return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
  }
}

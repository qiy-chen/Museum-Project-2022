package ca.mcgill.ecse321.MuseumBackend.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LoanExceptionHandler extends ResponseEntityExceptionHandler{
  @ExceptionHandler(LoanException.class)
  public ResponseEntity<String> handleLoanException(LoanException ex){
    return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
  }
}

package ca.mcgill.ecse321.MuseumBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TicketNotFoundExceptionAdvice {
  @ResponseBody
  @ExceptionHandler(TicketNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(TicketNotFoundException ex) {
    return ex.getMessage();
  }
}

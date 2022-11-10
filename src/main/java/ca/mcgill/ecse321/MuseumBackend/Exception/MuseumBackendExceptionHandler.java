package ca.mcgill.ecse321.MuseumBackend.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MuseumBackendExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MuseumBackendException.class)
	public ResponseEntity<String> handleMuseumBackendException(MuseumBackendException ex) {
		return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
	}
	
}

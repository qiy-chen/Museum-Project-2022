package ca.mcgill.ecse321.MuseumBackend.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class MuseumBackendException extends RuntimeException {

	private HttpStatus status;
	
	public MuseumBackendException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
	
}
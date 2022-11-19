package ca.mcgill.ecse321.MuseumBackend.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class DisplayException extends RuntimeException {

    private HttpStatus status;
    
    public DisplayException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    
    public HttpStatus getStatus() {
        return this.status;
    }
    
}
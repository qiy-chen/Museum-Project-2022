package ca.mcgill.ecse321.MuseumBackend.Exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ArtworkException extends RuntimeException {

    private HttpStatus status;
    
    public ArtworkException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    
    public HttpStatus getStatus() {
        return this.status;
    }
    
}
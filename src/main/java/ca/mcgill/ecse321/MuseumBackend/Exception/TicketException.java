package ca.mcgill.ecse321.MuseumBackend.Exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class TicketException extends RuntimeException{
    private HttpStatus status;
    
    public TicketException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    
    public HttpStatus getStatus() {
        return this.status;
    }
}

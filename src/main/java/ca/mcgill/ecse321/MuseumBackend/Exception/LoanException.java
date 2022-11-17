package ca.mcgill.ecse321.MuseumBackend.Exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class LoanException extends RuntimeException{
  private HttpStatus status;
  public LoanException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }
  public HttpStatus getStatus() {
    return this.status;
  }
}

package ca.mcgill.ecse321.MuseumBackend.dto;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import java.time.LocalDateTime;

public class TicketRequestDto {

  private LocalDateTime ticketDate;

  private double price;

  
  public TicketRequestDto() {}
  public TicketRequestDto(LocalDateTime ticketDate, double price) {
    this.ticketDate = ticketDate;
    this.price = price;
  }
  
  public void setTicketDate(LocalDateTime newDate) {
    ticketDate = newDate;
  }
  public LocalDateTime getTicketDate() {
    return ticketDate;
  }
  
  public void setPrice(double newPrice) {
    price = newPrice;
  }
  public double getPrice() {
    return price;
  }
  

  
  public Ticket toModel() {
    Ticket ticket = new Ticket();
    ticket.setPrice(price);
    ticket.setTicketDate(ticketDate);
    return ticket;
  }
  
}

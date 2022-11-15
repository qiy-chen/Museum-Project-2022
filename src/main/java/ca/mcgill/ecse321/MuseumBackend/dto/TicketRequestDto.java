package ca.mcgill.ecse321.MuseumBackend.dto;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import java.time.LocalDateTime;

public class TicketRequestDto {

  private LocalDateTime ticketDate;

  private double price;

  private int ticketId;
  
  public TicketRequestDto() {}
  public TicketRequestDto(int ticketId, LocalDateTime ticketDate, double price) {
    this.ticketId = ticketId;
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
  
  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }
  public int getTicketId() {
    return ticketId;
  }
  
  public Ticket toModel() {
    Ticket ticket = new Ticket();
    ticket.setPrice(price);
    ticket.setTicketDate(ticketDate);
    ticket.setTicketId(ticketId);
    return ticket;
  }
  
}

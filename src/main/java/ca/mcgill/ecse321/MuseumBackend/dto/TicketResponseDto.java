package ca.mcgill.ecse321.MuseumBackend.dto;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import java.sql.Date;

public class TicketResponseDto {
  private Date ticketDate;
  private double price;
  private int ticketId;
  public TicketResponseDto(Ticket ticket) {
    this.ticketId = ticket.getTicketId();
    this.ticketDate = ticket.getTicketDate();
    this.price = ticket.getPrice();
  }

  public Date getTicketDate() {
    return ticketDate;
  }
  

  public double getPrice() {
    return price;
  }
  
  public double getTicketId() {
    return ticketId;
  }
  
  
}

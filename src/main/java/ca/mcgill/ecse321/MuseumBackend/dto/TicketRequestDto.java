package ca.mcgill.ecse321.MuseumBackend.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import java.sql.Date;

public class TicketRequestDto {
  @NotNull
  private Date ticketDate;
  @NotNull
  private double price;
  @NotBlank
  @NotNull
  private int ticketId;
  
  public void setTicketDate(Date newDate) {
    ticketDate = newDate;
  }
  public Date getTicketDate() {
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
  public double getTicketId() {
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

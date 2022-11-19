package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Ticket;

import java.time.LocalDateTime;

public class TicketResponseDto {
  private LocalDateTime ticketDate ;
  private double price ;
  private int ticketId;
  public TicketResponseDto() {}
  public TicketResponseDto(Ticket ticket) {
    this.ticketId = ticket.getTicketId();
    this.ticketDate = ticket.getTicketDate();
    this.price = ticket.getPrice();
  }

  public LocalDateTime getTicketDate() {
    return ticketDate;
  }
  

  public double getPrice() {
    return price;
  }
  
  public int getTicketId() {
    return ticketId;
  }
  
  
}

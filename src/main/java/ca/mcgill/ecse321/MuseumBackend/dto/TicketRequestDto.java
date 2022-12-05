package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketRequestDto {

  private String ticketDate;

  private double price;

  
  public TicketRequestDto() {}
  public TicketRequestDto(String ticketDate, double price) {
    this.ticketDate = ticketDate;
    this.price = price;
  }
  
  public void setTicketDate(String newDate) {
    ticketDate = newDate;
  }
  public String getTicketDate() {
    return ticketDate;
  }
  
  public void setPrice(double newPrice) {
    price = newPrice;
  }
  public double getPrice() {
    return price;
  }
  

  
  public Ticket toModel() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
    Ticket ticket = new Ticket();
    ticket.setPrice(price);
    this.ticketDate += " 0:00";
    ticket.setTicketDate(LocalDateTime.parse(ticketDate,formatter));
    return ticket;
  }
  
}

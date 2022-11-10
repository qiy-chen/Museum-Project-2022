package ca.mcgill.ecse321.MuseumBackend.controller;

import java.sql.Date;

public class TicketDTO {
  private Date ticketDate;
  private double price;
  private int ticketId;
  public TicketDTO(int ticketId, Date ticketDate, double price) {
    this.ticketId = ticketId;
    this.ticketDate = ticketDate;
    this.price = price;
  }
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
  
}

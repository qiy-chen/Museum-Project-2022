package ca.mcgill.ecse321.MuseumBackend.controller;

public class TicketNotFoundException extends RuntimeException{
  public TicketNotFoundException (int id) {
    super("Could not find ticket " + id);
  }
}

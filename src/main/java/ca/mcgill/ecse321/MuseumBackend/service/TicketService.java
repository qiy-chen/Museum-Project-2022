package ca.mcgill.ecse321.MuseumBackend.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.Exception.TicketException;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;

@Service
public class TicketService {
  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private CustomerService customerService;

  @Transactional
  public List<Ticket> getAllTickets() {
    List<Ticket> tickets = toList(ticketRepository.findAll());
    return tickets;
  }

  @Transactional
  public List<Ticket> getAllTicketsFromCustomer(int customerId) {
    List<Ticket> tickets = toList(ticketRepository.findAll());
    List<Ticket> ticketsdto = new ArrayList<Ticket>();
    for (Ticket ticket:tickets) {
      if (ticket.getCustomer().getPersonRoleId()==customerId) {
        ticketsdto.add(ticket);
      }
    }
    return ticketsdto;
  }

  @Transactional
  public Ticket getTicketByTicketId(int id) {
    Ticket ticket = ticketRepository.findTicketByTicketId(id);
    if (ticket == null) {
      throw new TicketException(HttpStatus.NOT_FOUND, "Ticket not found.");
    }
    return ticket;
  }

  @Transactional
  public Ticket createTicket(Ticket ticket) {
    if (ticket.getTicketDate()==null||ticket.getPrice()<0) {
      throw new TicketException(HttpStatus.BAD_REQUEST, "The ticket to be created contains invalid data.");
    }
    return ticketRepository.save(ticket);
  }

  @Transactional
  public Ticket replaceTicket(Ticket ticket, int id) {
    //Replace old ticket with id, 'id', with the new ticket
    Ticket oldTicket = ticketRepository.findTicketByTicketId(id);
    if (ticket.getTicketDate()==null||ticket.getPrice()<0) {
      throw new TicketException(HttpStatus.BAD_REQUEST, "The ticket to be created contains invalid data.");
    }
    else if (oldTicket == null) {
      //Create a new ticket
      ticket.setTicketId(id);
      return ticketRepository.save(ticket);
    }
    //Replace old ticket with new values
    oldTicket.setPrice(ticket.getPrice());
    oldTicket.setTicketDate(ticket.getTicketDate());
    return ticketRepository.save(oldTicket);

  }

  @Transactional
  public boolean deleteTicket(int id) {
    boolean success = false;
    Ticket canceledTicket = getTicketByTicketId(id);
    canceledTicket.delete();
    ticketRepository.delete(canceledTicket);
    success = true;
    return success;
  }


  @Transactional
  public boolean cancelTicket(int ticketId, int customerId) {
    boolean success = false;
    Ticket canceledTicket = getTicketByTicketId(ticketId);
    Customer customer = customerService.getCustomerById(customerId);
    //Check if it's the owner that is canceling
    if (canceledTicket.getCustomer().equals(customer)) {
      //Allow canceling 3 days before the ticket date
      LocalDateTime ticketDate = canceledTicket.getTicketDate();
      LocalDateTime cancelDate = ticketDate.minus(Duration.ofDays(3));
      LocalDateTime currentDate = getCurrentDate();
      if (currentDate.isBefore(cancelDate)) {
        canceledTicket.delete();
        ticketRepository.delete(canceledTicket);
        success = true;
        return success;
      }
      else {
        throw new TicketException(HttpStatus.FORBIDDEN, "Too late to make cancelation.");
      }
    }
    else {
      throw new TicketException(HttpStatus.FORBIDDEN, "Wrong customer id.");
    }

  }
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    if (iterable == null) return resultList;
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }

  public LocalDateTime getCurrentDate() {
    return LocalDateTime.now();
  }
}

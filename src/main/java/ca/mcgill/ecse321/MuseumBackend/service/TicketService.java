package ca.mcgill.ecse321.MuseumBackend.service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.Exception.TicketException;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;

@Service
public class TicketService {
  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private CustomerService customerService;
  @Autowired
  private MuseumService museumService;

  @Transactional
  public List<Ticket> getAllTickets() {
    List<Ticket> tickets = toList(ticketRepository.findAll());
    if (tickets.size() == 0) {
      throw new TicketException(HttpStatus.NOT_FOUND, "Ticket not found.");
    }
    return tickets;
  }

  @Transactional
  public List<Ticket> getAllTicketsFromCustomer(int customerId) {
    List<Ticket> tickets = toList(ticketRepository.findAll());
    if (tickets.size() == 0) {
      throw new TicketException(HttpStatus.NOT_FOUND, "Ticket not found.");
    }
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
    if (ticketRepository.findTicketByTicketId(ticket.getTicketId()) != null) {
      throw new TicketException(HttpStatus.CONFLICT, "A ticket with the given id already exists.");
    }
    else if (ticket.getTicketDate()==null||ticket.getPrice()<0) {
      throw new TicketException(HttpStatus.BAD_REQUEST, "The ticket to be created contains invalid data.");
    }
    return ticketRepository.save(ticket);
  }

  @Transactional
  public Ticket replaceTicket(Ticket ticket, int id) {
    //Replace old ticket with id, 'id', with the new one, 'ticket'
    Ticket oldTicket = ticketRepository.findTicketByTicketId(id);
    if (oldTicket == null) {
      throw new TicketException(HttpStatus.NOT_FOUND, "Ticket not found.");
    }
    else if (ticketRepository.findTicketByTicketId(ticket.getTicketId()) != null) {
      throw new TicketException(HttpStatus.CONFLICT, "A ticket with the given id already exists.");
    }
    else if (ticket.getTicketDate()==null||ticket.getPrice()<0) {
      throw new TicketException(HttpStatus.BAD_REQUEST, "The ticket to be created contains invalid data.");
    }
    oldTicket.setCustomer(ticket.getCustomer());
    oldTicket.setMuseum(ticket.getMuseum());
    oldTicket.setPrice(ticket.getPrice());
    oldTicket.setTicketDate(ticket.getTicketDate());
    oldTicket.setTicketId(ticket.getTicketId());
    return ticketRepository.save(oldTicket);

  }

  @Transactional
  public void deleteTicket(int id) {
    ticketRepository.deleteById(id);
  }

  @Transactional
  public void cancelTicket(int ticketId, int customerId) {
    Ticket canceledTicket = ticketRepository.findTicketByTicketId(ticketId);
    Customer customer = customerService.getCustomerById(customerId);
    //Check if it's the owner that is canceling
    if (canceledTicket.getCustomer().equals(customer)) {
      //Allow canceling 3 days before the ticket date
      LocalDateTime ticketDate = canceledTicket.getTicketDate();
      LocalDateTime cancelDate = ticketDate.minus(Duration.ofDays(3));
      LocalDateTime currentDate = getCurrentDate();
      if (currentDate.isBefore(cancelDate)) {
        deleteTicket(ticketId);
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

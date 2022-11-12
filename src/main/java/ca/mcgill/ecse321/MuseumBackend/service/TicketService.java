package ca.mcgill.ecse321.MuseumBackend.service;

import java.sql.Date;
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
    List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll();
    if (tickets.size() == 0) {
      throw new TicketException(HttpStatus.NOT_FOUND, "Ticket not found.");
    }
    return tickets;
  }

  @Transactional
  public List<Ticket> getAllTicketsFromCustomer(int customerId) {
    List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll();
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
  public Ticket createTicket(TicketRequestDto ticket,int customerId, int museumId) {
    if (ticketRepository.findTicketByTicketId(ticket.getTicketId()) != null) {
      throw new TicketException(HttpStatus.CONFLICT, "A ticket with the given id already exists.");
    }
    Customer customer = customerService.getCustomerById(customerId);
    Museum museum = museumService.getMuseumById(museumId);
    Ticket newTicket = new Ticket();
    newTicket.setCustomer(customer);
    newTicket.setMuseum(museum);
    newTicket.setPrice(ticket.getPrice());
    newTicket.setTicketDate(ticket.getTicketDate());
    newTicket.setTicketId(ticket.getTicketId());
    newTicket = ticketRepository.save(newTicket);
    return newTicket;
  }

  @Transactional
  public Ticket replaceTicket(TicketRequestDto newTicket, int id, int customerId, int museumId) {
    //Replace old ticket with id 'id' with the new one, 'newTicket'
    Ticket oldTicket = ticketRepository.findTicketByTicketId(id);
    if (oldTicket == null) {
      throw new TicketException(HttpStatus.NOT_FOUND, "Ticket not found.");
    }
    Customer newCustomer = customerService.getCustomerById(customerId);

    Museum museum = museumService.getMuseumById(museumId);

    oldTicket.setCustomer(newCustomer);
    oldTicket.setMuseum(museum);
    oldTicket.setPrice(newTicket.getPrice());
    oldTicket.setTicketDate(newTicket.getTicketDate());
    oldTicket.setTicketId(newTicket.getTicketId());
    return new TicketResponseDto(ticketRepository.save(oldTicket));

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
      Date ticketDate = canceledTicket.getTicketDate();
      Calendar c = Calendar.getInstance(); 
      c.setTime(ticketDate); 
      c.add(Calendar.DATE, -3);
      Date cancelDate = (Date) c.getTime();
      Date currentDate = new Date(System.currentTimeMillis());
      if (currentDate.before(cancelDate)) {
        deleteTicket(ticketId);
      }
    }

  }
}

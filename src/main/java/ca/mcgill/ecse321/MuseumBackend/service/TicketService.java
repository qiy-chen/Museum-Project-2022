package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ca.mcgill.ecse321.MuseumBackend.exception.TicketException;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;

@Service
public class TicketService {
  @Autowired
  TicketRepository ticketRepository;
  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  MuseumRepository museumRepository;

  @Transactional
  public Ticket getTicketByTicketId(int id) {
    Ticket ticket = ticketRepository.findTicketByTicketId(id);
    if (ticket == null) {
      throw new TicketException(HttpStatus.NOT_FOUND, "Ticket not found.");
    }
    return ticket;
  }

  @Transactional
  public Ticket createTicket(Ticket ticket,int customerId, Museum museum) {
    // This isn't an ideal way of checking for conflicts because it's not
    // thread-safe and it requires an extra DB request. In practice, it might be
    // better to use an integer ID for events, put a unique constraint on the name,
    // and catch the DataIntegrityViolationException from the repository.
    
    Customer customer = customerRepository.findCustomerByPersonRoleId(customerId);
    if (ticketRepository.findTicketByTicketId(ticket.getTicketId()) != null) {
      throw new TicketException(HttpStatus.CONFLICT, "A ticket with the given id already exists.");
    }
    if (customer == null) {
      throw new TicketException(HttpStatus.CONFLICT, "Customer not found.");
    }
    

    Ticket newTicket = ticket;
    newTicket.setCustomer(customer);
    newTicket.setMuseum(museum);
    customer = customerRepository.save(customer);
    ticket = ticketRepository.save(ticket);
    return ticket;
  }

  @Transactional
  public Ticket replaceTicket(Ticket newTicket, int id, int customerId, Museum museum) {
    Ticket oldTicket = ticketRepository.findTicketByTicketId(id);
    if (oldTicket == null) {
      throw new TicketException(HttpStatus.NOT_FOUND, "Ticket not found.");
    }
    if (customerRepository.findCustomerByPersonRoleId(customerId) == null) {
      throw new TicketException(HttpStatus.CONFLICT, "Customer to be assigned to the ticket not found.");
    }
    Customer oldCustomer = customerRepository.findCustomerByPersonRoleId(oldTicket.getCustomer().getPersonRoleId());

    Customer newCustomer = customerRepository.findCustomerByPersonRoleId(customerId);

    oldTicket.setCustomer(newCustomer);
    oldTicket.setMuseum(museum);
    oldTicket.setPrice(newTicket.getPrice());
    oldTicket.setTicketDate(newTicket.getTicketDate());
    oldTicket.setTicketId(newTicket.getTicketId());
    customerRepository.save(newCustomer);
    customerRepository.save(oldCustomer);
    return ticketRepository.save(oldTicket);

  }

  @Transactional
  void deleteTicket(int id) {
    ticketRepository.deleteById(id);
  }
}

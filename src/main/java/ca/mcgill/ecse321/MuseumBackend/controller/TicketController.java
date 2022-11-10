package ca.mcgill.ecse321.MuseumBackend.controller;


import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;

@RestController
public class TicketController {
  private TicketRepository ticketRepository;
  private CustomerRepository customerRepository;
  
  TicketController(TicketRepository repository, CustomerRepository customerRepository){
    this.ticketRepository = repository;
    this.customerRepository = customerRepository;
  }
  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/tickets")
  public List<Ticket> getAll() {
    return (List<Ticket>) ticketRepository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/tickets")
  public Ticket newTicket(@RequestBody Ticket newTicket) {
    return ticketRepository.save(newTicket);
  }

  // Single item
  
  @GetMapping("/tickets/{id}")
  public Ticket getTicketFromId(@PathVariable int id) {
    
    Ticket ticket = ticketRepository.findTicketByTicketId(id);
    if (ticket!=null) {
      return ticket;
    }
    else throw new TicketNotFoundException(id);
    }

  @PutMapping("/tickets/{id}")
  public Ticket replaceTicket(@RequestBody Ticket newTicket, @PathVariable int id) {
    
    Ticket oldTicket = ticketRepository.findTicketByTicketId(id);
    
    if (oldTicket!=null) {
      oldTicket.setCustomer(newTicket.getCustomer());
      oldTicket.setMuseum(newTicket.getMuseum());
      oldTicket.setPrice(newTicket.getPrice());
      oldTicket.setTicketDate(newTicket.getTicketDate());
      return ticketRepository.save(oldTicket);
    }
    else {
      newTicket.setTicketId(id);
      return ticketRepository.save(newTicket);
    }
      
  }

  @DeleteMapping("/tickets/{id}")
  void deleteTicket(@PathVariable int id) {
    ticketRepository.deleteById(id);
  }
  
  //The following methods are dedicated for use-cases scenarios only (for the regular web user)
  @GetMapping("/persons/{roleId}")
  public List<TicketDTO> getTicketsFromCustomer(@PathVariable int roleId){
    List<Ticket> allTickets = (List<Ticket>) ticketRepository.findAll();
    List<TicketDTO> customerTickets = new ArrayList<TicketDTO>();
    for (Ticket ticket: allTickets) {
      if (ticket.getCustomer().getPersonRoleId()==roleId) {
        TicketDTO ticketDTO = new TicketDTO(ticket.getTicketId(),ticket.getTicketDate(),ticket.getPrice());
        customerTickets.add(ticketDTO);
      }
    }
    return customerTickets;
  }
  
  @PostMapping("/persons/{roleId}")
  public void purchaseTicket(@PathVariable int personRoleId, Date ticketDate, double price, int ticketId, Museum museum) {
    Ticket purchasedTicket = ticketRepository.findTicketByTicketId(ticketId);
    //Need to also check if the ticket is available and not purchased
    Customer customer = customerRepository.findCustomerByPersonRoleId(personRoleId);
    if (purchasedTicket==null && customer!=null) {
      purchasedTicket = new Ticket();
      purchasedTicket.setCustomer(customer);
      purchasedTicket.setMuseum(museum);
      purchasedTicket.setPrice(price);
      purchasedTicket.setTicketDate(ticketDate);
      purchasedTicket.setTicketId(ticketId);
      ticketRepository.save(purchasedTicket);
    }
    else {
      //Throw customer not found or ticket id already existent
    }
  }
  
  @DeleteMapping("/persons/{roleId}")
  void cancelTicket(@PathVariable int personRoleId, @RequestBody int ticketId) {
    Ticket canceledTicket = ticketRepository.findTicketByTicketId(ticketId);
    Customer customer = customerRepository.findCustomerByPersonRoleId(personRoleId);
    if (canceledTicket!=null && customer!=null) {
      if (canceledTicket.getCustomer()==customer) {
        //Allow canceling 3 days before the ticket date
        Date cancelDate = new Date(0);
        Calendar c = Calendar.getInstance(); 
        c.setTime(cancelDate); 
        c.add(Calendar.DATE, 3);
        cancelDate = (Date) c.getTime();
        Date currentDate = new Date(System.currentTimeMillis());
        if (currentDate.before(cancelDate)) {
          canceledTicket.delete();
          ticketRepository.save(canceledTicket);
          customerRepository.save(customer);
        }
        else {
          //throw too late to cancel exception
        }
      }
      else {
        //throw permission denied exception
      }
    }
    else throw new TicketNotFoundException(ticketId);
  }
}

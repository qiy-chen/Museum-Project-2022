package ca.mcgill.ecse321.MuseumBackend.controller;


import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketDTO;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.exception.TicketNotFoundException;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;
import ca.mcgill.ecse321.MuseumBackend.service.TicketService;

@RestController
public class TicketController {
  @Autowired
  TicketService ticketService;
  
  // Aggregate root
  // tag::get-aggregate-root[]
//  @GetMapping("/tickets")
//  public List<Ticket> getAll() {
//    return (List<Ticket>) ticketRepository.findAll();
//  }
  // end::get-aggregate-root[]

  @PostMapping("/tickets")
  public TicketResponseDto createTicket(@Valid @RequestBody TicketRequestDto newTicket, @RequestBody int customerId, Museum museum) {
    Ticket ticket = newTicket.toModel();
    return new TicketResponseDto(ticketService.createTicket(ticket, customerId, museum));
  }

  // Single item
  
//  @GetMapping("/tickets/{id}")
//  public Ticket getTicketFromId(@PathVariable int id) {
//    
//    Ticket ticket = ticketRepository.findTicketByTicketId(id);
//    if (ticket!=null) {
//      return ticket;
//    }
//    else throw new TicketNotFoundException(id);
//    }

//  @PutMapping("/tickets/{id}")
//  public Ticket replaceTicket(@RequestBody Ticket newTicket, @PathVariable int id) {
//    
//    Ticket oldTicket = ticketRepository.findTicketByTicketId(id);
//    
//    if (oldTicket!=null) {
//      oldTicket.setCustomer(newTicket.getCustomer());
//      oldTicket.setMuseum(newTicket.getMuseum());
//      oldTicket.setPrice(newTicket.getPrice());
//      oldTicket.setTicketDate(newTicket.getTicketDate());
//      return ticketRepository.save(oldTicket);
//    }
//    else {
//      newTicket.setTicketId(id);
//      return ticketRepository.save(newTicket);
//    }
//      
//  }

//  @DeleteMapping("/tickets/{id}")
//  void deleteTicket(@PathVariable int id) {
//    ticketRepository.deleteById(id);
//  }
  
  //The following methods are dedicated for use-cases scenarios only (for the regular web user)
  //Browse ticket
  @GetMapping("/persons/{roleId}")
  public List<TicketResponseDto> getTicketsFromCustomer(@PathVariable int roleId){
    List<Ticket> allTickets = (List<Ticket>) ticketRepository.findAll();
    List<TicketResponseDto> customerTickets = new ArrayList<TicketResponseDto>();
    for (Ticket ticket: allTickets) {
      if (ticket.getCustomer().getPersonRoleId()==roleId) {
        TicketResponseDto ticketDTO = new TicketResponseDto(ticket);
        customerTickets.add(ticketDTO);
      }
    }
    return customerTickets;
  }
  
  //Purchase ticket
  @PostMapping("/persons/{roleId}")
  public TicketResponseDto purchaseTicket(@PathVariable int personRoleId, Ticket ticket) {
    Ticket purchasedTicket = ticketService.createTicket(ticket, personRoleId);
    return new TicketResponseDto(purchasedTicket);
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

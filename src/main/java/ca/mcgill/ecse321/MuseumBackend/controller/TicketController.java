package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.IdRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.service.CustomerService;
import ca.mcgill.ecse321.MuseumBackend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class TicketController {
  @Autowired
  TicketService ticketService;
  @Autowired
  private CustomerService customerService;

  @GetMapping("/tickets/{id}")
  public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable int id) {
    Ticket ticket = ticketService.getTicketByTicketId(id);
    return new ResponseEntity<TicketResponseDto>(new TicketResponseDto(ticket), HttpStatus.OK);
  }
  
  @GetMapping("/tickets")
  public ResponseEntity<List<TicketResponseDto>> getAllTickets() {
    List<Ticket> listTickets = ticketService.getAllTickets();
    List<TicketResponseDto> listResponseTickets = new ArrayList<TicketResponseDto>();
    for (Ticket ticket:listTickets) {
      listResponseTickets.add(new TicketResponseDto(ticket));
    }
    return new ResponseEntity<List<TicketResponseDto>>(listResponseTickets, HttpStatus.OK);
  }
  
  @PostMapping("/tickets")
  public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketRequestDto newTicketDto) {
    Ticket newTicket = newTicketDto.toModel();
    newTicket = ticketService.createTicket(newTicket);
    return new ResponseEntity<TicketResponseDto>(new TicketResponseDto(newTicket), HttpStatus.CREATED);
  }

  @PutMapping("/tickets/{ticketId}")
  public ResponseEntity<TicketResponseDto> replaceTicket(@PathVariable int ticketId, @Valid @RequestBody TicketRequestDto newTicketDto) {
    Ticket newTicket = newTicketDto.toModel();
    newTicket = ticketService.replaceTicket(newTicket, ticketId);
    return new ResponseEntity<TicketResponseDto>(new TicketResponseDto(newTicket), HttpStatus.OK);
  }

  @DeleteMapping("/tickets/{id}")
  public ResponseEntity<TicketResponseDto> deleteTicket(@PathVariable int id) {
    ticketService.deleteTicket(id);
    return new ResponseEntity<TicketResponseDto>(HttpStatus.OK);
  }


  //The following methods are dedicated for use-cases scenarios only (for the regular web user)
  //Browse ticket
  @GetMapping("/customers/{roleId}")
  public ResponseEntity<List<TicketResponseDto>> getTicketsFromCustomer(@PathVariable int roleId){
    List<Ticket> customerTickets = ticketService.getAllTicketsFromCustomer(roleId);
    List<TicketResponseDto> customerTicketsDto = new ArrayList<TicketResponseDto>();
    for (Ticket ticket: customerTickets) {
      customerTicketsDto.add(new TicketResponseDto(ticket));
    }
    return new ResponseEntity<List<TicketResponseDto>>(customerTicketsDto, HttpStatus.OK);
  }

  //Purchase ticket
  @PostMapping("/customers/{roleId}")
  public ResponseEntity<TicketResponseDto> purchaseTicket(@PathVariable int roleId, @RequestBody TicketRequestDto newTicketDto) {
    Ticket newTicket = newTicketDto.toModel();
    Customer customer = customerService.getCustomerById(roleId);
    newTicket.setCustomer(customer);
    newTicket = ticketService.createTicket(newTicket);
    return new ResponseEntity<TicketResponseDto>(new TicketResponseDto(newTicket),HttpStatus.CREATED);
  }

  @DeleteMapping("/customers/{roleId}")
  public ResponseEntity<TicketResponseDto> cancelTicket(@PathVariable int roleId, @RequestBody IdRequestDto ticketId) {
    //ticketService.deleteTicket(ticketId.getId());
    ticketService.cancelTicket(ticketId.getId(),roleId);
    return new ResponseEntity<TicketResponseDto>(HttpStatus.OK);
  }
}

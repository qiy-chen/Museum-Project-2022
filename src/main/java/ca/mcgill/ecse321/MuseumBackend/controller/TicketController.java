package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.service.CustomerService;
import ca.mcgill.ecse321.MuseumBackend.service.MuseumService;
import ca.mcgill.ecse321.MuseumBackend.service.TicketService;

@RestController
public class TicketController {
  @Autowired
  TicketService ticketService;
  @Autowired
  private CustomerService customerService;
  @Autowired
  private MuseumService museumService;

  @GetMapping("/tickets/{id}")
  public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable int id) {
    Ticket ticket = ticketService.getTicketByTicketId(id);
    return new ResponseEntity<TicketResponseDto>(new TicketResponseDto(ticket), HttpStatus.OK);
  }
  @PostMapping("/tickets")
  public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketRequestDto newTicketDto, @RequestBody int customerId, int museumId) {
    Ticket newTicket = newTicketDto.toModel();
    Museum museum = museumService.getMuseumById(museumId);
    Customer customer = customerService.getCustomerById(customerId);
    if (museum==null||customer==null) {
      return new ResponseEntity<TicketResponseDto>(HttpStatus.BAD_REQUEST);
    }
    newTicket.setMuseum(museum);
    newTicket.setCustomer(customer);
    newTicket = ticketService.createTicket(newTicket);
    return new ResponseEntity<TicketResponseDto>(new TicketResponseDto(newTicket), HttpStatus.CREATED);
  }

  @PutMapping("/tickets/{id}")
  public ResponseEntity<TicketResponseDto> replaceTicket(@PathVariable int id,@RequestBody int customerId, int museumId,@RequestBody TicketRequestDto newTicketDto) {
    Ticket newTicket = newTicketDto.toModel();
    Museum museum = museumService.getMuseumById(museumId);
    Customer customer = customerService.getCustomerById(customerId);
    if (museum==null||customer==null) {
      return new ResponseEntity<TicketResponseDto>(HttpStatus.BAD_REQUEST);
    }
    newTicket.setMuseum(museum);
    newTicket.setCustomer(customer);
    newTicket = ticketService.replaceTicket(newTicket, id);
    return new ResponseEntity<TicketResponseDto>(new TicketResponseDto(newTicket), HttpStatus.OK);
  }

  @DeleteMapping("/tickets/{id}")
  public ResponseEntity<TicketResponseDto> deleteTicket(@PathVariable int id) {
    ticketService.deleteTicket(id);
    return new ResponseEntity<TicketResponseDto>(HttpStatus.OK);
  }


  //The following methods are dedicated for use-cases scenarios only (for the regular web user)
  //Browse ticket
  @GetMapping("/persons/{roleId}")
  public ResponseEntity<List<TicketResponseDto>> getTicketsFromCustomer(@PathVariable int roleId){
    List<Ticket> customerTickets = ticketService.getAllTicketsFromCustomer(roleId);
    List<TicketResponseDto> customerTicketsDto = new ArrayList<TicketResponseDto>();
    for (Ticket ticket: customerTickets) {
      customerTicketsDto.add(new TicketResponseDto(ticket));
    }
    return new ResponseEntity<List<TicketResponseDto>>(customerTicketsDto, HttpStatus.OK);
  }

  //Purchase ticket
  @PostMapping("/persons/{roleId}")
  public ResponseEntity<TicketResponseDto> purchaseTicket(@PathVariable int customerId, int museumId, TicketRequestDto newTicketDto) {
    Ticket newTicket = newTicketDto.toModel();
    Museum museum = museumService.getMuseumById(museumId);
    Customer customer = customerService.getCustomerById(customerId);
    if (museum==null||customer==null) {
      return new ResponseEntity<TicketResponseDto>(HttpStatus.BAD_REQUEST);
    }
    newTicket.setMuseum(museum);
    newTicket.setCustomer(customer);
    newTicket = ticketService.createTicket(newTicket);
    return new ResponseEntity<TicketResponseDto>(new TicketResponseDto(newTicket),HttpStatus.OK);
  }

  @DeleteMapping("/persons/{roleId}")
  public ResponseEntity<TicketResponseDto> cancelTicket(@PathVariable int customerId, @RequestBody int ticketId) {
    ticketService.cancelTicket(ticketId,customerId);
    return new ResponseEntity<TicketResponseDto>(HttpStatus.OK);
  }
}

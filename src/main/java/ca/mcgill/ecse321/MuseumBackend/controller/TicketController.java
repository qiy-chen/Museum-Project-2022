package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.service.TicketService;

@RestController
public class TicketController {
  @Autowired
  TicketService ticketService;


  @GetMapping("/tickets/{id}")
  public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable int id) {
    TicketResponseDto ticket = ticketService.getTicketByTicketId(id);
    return new ResponseEntity<TicketResponseDto>(ticket, HttpStatus.OK);
  }
  @PostMapping("/tickets")
  public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketRequestDto newTicket, @RequestBody int customerId, int museumId) {
    TicketResponseDto ticket = ticketService.createTicket(newTicket, customerId, museumId);
    return new ResponseEntity<TicketResponseDto>(ticket, HttpStatus.CREATED);
  }

  @PutMapping("/tickets/{id}")
  public ResponseEntity<TicketResponseDto> replaceTicket(@PathVariable int id,@RequestBody int customerId, int museumId,@RequestBody TicketRequestDto newTicket) {
    TicketResponseDto ticket = ticketService.replaceTicket(newTicket, id, customerId, museumId);
    return new ResponseEntity<TicketResponseDto>(ticket, HttpStatus.OK);
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
    List<TicketResponseDto> customerTickets = ticketService.getAllTicketsFromCustomer(roleId);
    return new ResponseEntity<List<TicketResponseDto>>(customerTickets, HttpStatus.OK);
  }

  //Purchase ticket
  @PostMapping("/persons/{roleId}")
  public ResponseEntity<TicketResponseDto> purchaseTicket(@PathVariable int personRoleId, int museumId, TicketRequestDto ticket) {
    TicketResponseDto purchasedTicket = ticketService.createTicket(ticket, personRoleId,museumId);
    return new ResponseEntity<TicketResponseDto>(purchasedTicket,HttpStatus.OK);
  }

  @DeleteMapping("/persons/{roleId}")
  public ResponseEntity<TicketResponseDto> cancelTicket(@PathVariable int personRoleId, @RequestBody int ticketId) {
    ticketService.cancelTicket(ticketId,personRoleId);
    return new ResponseEntity<TicketResponseDto>(HttpStatus.OK);
  }
}

package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;

@SpringBootTest
public class TicketRepositoryTests {
  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private MuseumRepository museumRepository;


  @AfterEach
  public void clearDatabase() {
    ticketRepository.deleteAll();
    customerRepository.deleteAll();
    museumRepository.deleteAll();
  }
  @Test
  public void testPersistAndLoadTicket() {
    //Setup museum instance
    int museumId = 0;
    Museum museumInstance = new Museum(museumId);

    //Setup customer
    int customerId = 2;
    Customer customer = new Customer(customerId);
    //Setup ticket
    double ticketPrice = 10.00;
    Date ticketDate = new Date(2);
    int ticketId = 33;
    Ticket ticket = new Ticket(ticketPrice, ticketId, ticketDate, museumInstance, customer);
    
    ticket = ticketRepository.save(ticket);
    
    //Set all values of objects to null
    ticket = null;
    customer = null;
    museumInstance = null;
    
    museumRepository.save(museumInstance);
    customerRepository.save(customer);
    //Search for the ticket in the database
    ticket = ticketRepository.findTicketByTicketId(ticketId);
    
    //Test if the values are correct
    assertNotNull(ticket);
    assertEquals(ticketId, ticket.getTicketId());
    assertEquals(ticketPrice, ticket.getPrice());
    assertEquals(ticketDate, ticket.getTicketDate());
    
    assertNotNull(ticket.getCustomer());
    assertEquals(customerId, ticket.getCustomer().getPersonRoleId());
    
    assertNotNull(ticket.getMuseum());
    assertEquals(museumId, ticket.getMuseum().getMuseumId());
    
  }
  
}
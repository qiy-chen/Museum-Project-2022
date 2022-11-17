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



  @AfterEach
  public void clearDatabase() {
    ticketRepository.deleteAll();
    customerRepository.deleteAll();
  }
  @Test
  public void testPersistAndLoadTicket() {

    //Setup customer
    Customer customer = new Customer();
    customer.setPersonRoleId(99);
    customer = customerRepository.save(customer);
    int customerId = customer.getPersonRoleId();
    
    //Setup ticket
    Ticket ticket = new Ticket();
    ticket.setPrice(10.00);
    ticket.setTicketDate(new Date(4));
    ticket.setCustomer(customer);
    ticket = ticketRepository.save(ticket);
    
    // get attributes
    int ticketId = ticket.getTicketId();
    double ticketPrice = ticket.getPrice();
    Date ticketDate = ticket.getTicketDate();
    
    //Set all values of objects to null
    ticket = null;
    customer = null;
    
    //Search for the ticket in the database
    ticket = ticketRepository.findTicketByTicketId(ticketId);
    
    //Test if the values are correct
    assertNotNull(ticket);
    assertEquals(ticketId, ticket.getTicketId());
    assertEquals(ticketPrice, ticket.getPrice());
    assertEquals(ticketDate.toString(), ticket.getTicketDate().toString());
    
    assertNotNull(ticket.getCustomer());
    assertEquals(customerId, ticket.getCustomer().getPersonRoleId());
    
  }
  
}
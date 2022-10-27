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
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.PersonRole;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;

@SpringBootTest
public class TicketRepositoryTests {
  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private MuseumRepository museumRepository;
  @Autowired
  private PersonRepository personRepository;


  @AfterEach
  public void clearDatabase() {
    ticketRepository.deleteAll();
    customerRepository.deleteAll();
    museumRepository.deleteAll();
    personRepository.deleteAll();
  }
  @Test
  public void testPersistAndLoadTicket() {
    //Setup museum instance
    int museumId = 0;
    Museum museumInstance = new Museum(museumId);

    //Setup Person
    String name = "Bob";
    Person person = new Person("exampleemail@mail.com", "12345", name, museumInstance);
    
    //Setup customer
    int customerId = 2;
    Customer customer = new Customer(customerId,person);
    //Setup ticket
    double ticketPrice = 10.00;
    Date ticketDate = new Date(2);
    int ticketId = 33;
    Ticket ticket = new Ticket(ticketPrice, ticketId, ticketDate, museumInstance, customer);
    
    museumRepository.save(museumInstance);
    personRepository.save(person);
    customerRepository.save(customer);
    ticket = ticketRepository.save(ticket);
    
    //Set all values of objects to null
    ticket = null;
    customer = null;
    museumInstance = null;
    

    //Search for the ticket in the database
    ticket = ticketRepository.findTicketByTicketId(ticketId);
    
    //Test if the values are correct
    assertNotNull(ticket);
    assertEquals(ticketId, ticket.getTicketId());
    assertEquals(ticketPrice, ticket.getPrice());
    assertEquals(ticketDate, ticket.getTicketDate());
    
    assertNotNull(ticket.getCustomer());
    assertEquals(customerId, ticket.getCustomer().getPersonRoleId());
    
    assertNotNull(ticket.getCustomer().getPerson());
    assertEquals(name, ticket.getCustomer().getPerson().getName());
    
    assertNotNull(ticket.getMuseum());
    assertEquals(museumId, ticket.getMuseum().getMuseumId());
    
  }
  
}
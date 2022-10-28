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
  //@Autowired
  //private PersonRepository personRepository;


  @AfterEach
  public void clearDatabase() {
    
    ticketRepository.deleteAll();
    customerRepository.deleteAll();
    //personRepository.deleteAll();
    museumRepository.deleteAll();
    
  }
  @Test
  public void testPersistAndLoadTicket() {
    //Setup museum instance
    int museumId = 5;
    Museum museumInstance = new Museum();
    museumInstance.setMuseumId(museumId);
    museumInstance = museumRepository.save(museumInstance);
    museumId = museumInstance.getMuseumId();
    //Setup Person
    //Person person = new Person();
    //person.setEmail("exampleemail@mail.com");
    //person.setMuseum(museumInstance);
    //person.setName("Bob");
    //person.setPassword("12345");
    
   // String name = person.getName();
    //person = personRepository.save(person);
    //Setup customer
    Customer customer = new Customer();
    //customer.setPerson(person);
    customer.setPersonRoleId(2);
    customer = customerRepository.save(customer);
    int customerId = customer.getPersonRoleId();
    
    //Setup ticket
    Ticket ticket = new Ticket();
    ticket.setMuseum(museumInstance);
    ticket.setPrice(10.00);
    ticket.setTicketDate(new Date(4));
    ticket.setCustomer(customer);
    ticket.setTicketId(13);
    
    ticket = ticketRepository.save(ticket);
    
    int ticketId = ticket.getTicketId();
    double ticketPrice = ticket.getPrice();
    Date ticketDate = ticket.getTicketDate();
    
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
    assertEquals(ticketDate.toString(), ticket.getTicketDate().toString());
    
    assertNotNull(ticket.getCustomer());
    assertEquals(customerId, ticket.getCustomer().getPersonRoleId());
    
    //assertNotNull(ticket.getCustomer().getPerson());
    //assertEquals(name, ticket.getCustomer().getPerson().getName());
    
    assertNotNull(ticket.getMuseum());
    assertEquals(museumId, ticket.getMuseum().getMuseumId());
    
  }
  
}
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
    int museumId = 5;
    Museum museumInstance = new Museum(museumId);
    museumRepository.save(museumInstance);
    //Setup Person
    Person person = new Person();
    person.setEmail("exampleemail@mail.com");
    person.setMuseum(museumInstance);
    person.setName("Bob");
    person.setPassword("12345");
    String name = person.getName();
    personRepository.save(person);
    //Setup customer
    Customer customer = new Customer();
    int customerId = customer.getPersonRoleId();
    customer.setPerson(person);
    customerRepository.save(customer);
    
    //Setup ticket
    Ticket ticket = new Ticket();
    ticket.setCustomer(customer);
    ticket.setMuseum(museumInstance);
    ticket.setPrice(10.00);
    ticket.setTicketDate(new Date(4));
    int ticketId = ticket.getTicketId();
    double ticketPrice = ticket.getPrice();
    Date ticketDate = ticket.getTicketDate();
    
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
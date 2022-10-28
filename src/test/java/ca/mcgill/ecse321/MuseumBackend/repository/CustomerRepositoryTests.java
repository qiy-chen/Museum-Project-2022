package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.PersonRole;
import ca.mcgill.ecse321.MuseumBackend.model.Person;


@SpringBootTest
public class CustomerRepositoryTests {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private MuseumRepository museumRepository;

  @Autowired
  private PersonRepository personRepository;
  
  @Autowired
  private TicketRepository ticketRepository;
  
  @AfterEach
  public void clearDatabase() {
      customerRepository.deleteAll();
      ticketRepository.deleteAll();
      museumRepository.deleteAll();
      personRepository.deleteAll();
  }

  @Test
  public void testPersistAndLoadCustomer() {
      // Create object
      String email = "elie.abdo@mail.mcgill.ca";
      String name = "Elie Abdo";
      String password = "B00BS123";
      Person elie = new Person();
      elie.setEmail(email);
      elie.setName(name);
      elie.setPassword(password);
      elie = personRepository.save(elie);
      elie.addPersonRole(new Customer());
      elie.getPersonRole(0).setPersonRoleId(123);
      Customer mood = (Customer) elie.getPersonRole(0);
      mood = customerRepository.save(mood);
      
      int customerId = mood.getPersonRoleId();
      mood = null;
      mood = customerRepository.findCustomerByPersonRoleId(customerId);
      assertNotNull(mood);
      assertEquals(customerId, mood.getPersonRoleId());
  }
  
}

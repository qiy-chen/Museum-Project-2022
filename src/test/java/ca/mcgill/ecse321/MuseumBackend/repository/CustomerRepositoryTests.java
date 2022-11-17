package ca.mcgill.ecse321.MuseumBackend.repository;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.PersonRole;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.model.Person;


@SpringBootTest
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        personRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadCustomer() {
        // Create object
        Customer elie = new Customer();

        // Save object
        customerRepository.save(elie);
        int EmployeeId = elie.getPersonRoleId();

        // Read object from database
        elie = null;
        elie = customerRepository.findCustomerByPersonRoleId(EmployeeId);

        // Assert that object has correct attributes
        assertNotNull(elie);
        assertEquals(EmployeeId, elie.getPersonRoleId());
    }
    
    @Test
    public void testCustomerToPersonReference() {
        
    	// Create object
        Customer elie = new Customer();
        customerRepository.save(elie); // save before adding art so that it is present for the foreign key when saving the artwork
        
        // create reference object
        String email = "sandie@hotmail.com";
        Person sandy = new Person();
        sandy.setEmail(email);
        personRepository.save(sandy);
        elie.setPerson(sandy);

        // Update object
        customerRepository.save(elie);
        int customerID = elie.getPersonRoleId();

        // Read object from database
        elie = customerRepository.findCustomerByPersonRoleId(customerID);

        // Assert that object has correct attributes
        assertNotNull(elie);
        assertEquals(customerID, elie.getPersonRoleId());
        assertEquals(email,elie.getPerson().getEmail());
    }
    
    
}

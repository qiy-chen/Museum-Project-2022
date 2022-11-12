package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class TicketIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    
    @Autowired
    private TicketRepository ticketRepo;
    @Autowired
    private MuseumRepository museumRepo;
    @Autowired
    private CustomerRepository customerRepo;
    
    Date ticketDate;
    int museumId;
    int customerId;
    
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        ticketRepo.deleteAll();
        customerRepo.deleteAll();
        museumRepo.deleteAll();
    }
    
    @Test
    public void testCreateAndGetTicket() {
      //Setup other repos
        museumId = 1;
        customerId = 10;
        Museum museum = new Museum();
        museum.setMuseumId(museumId);
        Customer customer = new Customer();
        customer.setPersonRoleId(customerId);
        museumRepo.save(museum);
        customerRepo.save(customer);
        
        ticketDate = getCurrentDate();
        int id = testCreateTicket();
        testGetTicket(id);
    }
    
    private int testCreateTicket() {
        
        ResponseEntity<TicketDto> response = client.postForEntity("/tickets", new TicketDto(ticketDate,10.00), TicketDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(ticketDate, response.getBody().getTicketDate(), "Response has correct date");
        assertTrue(response.getBody().getTicketId() > 0, "Response has valid ID");
        
        return response.getBody().getTicketId();
    }
    
    private void testGetTicket(int id) {
        ResponseEntity<TicketDto> response = client.getForEntity("/tickets/" + id, TicketDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(ticketDate, response.getBody().getTicketDate(), "Response has correct date");
        assertTrue(response.getBody().getTicketId() == id, "Response has correct ID");
    }
    
    @Test
    public void testCreateInvalidTicket() {
        ResponseEntity<String> response = client.postForEntity("/tickets", new TicketDto(), String.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }
    
    @Test
    public void testGetInvalidTicket() {
        ResponseEntity<String> response = client.getForEntity("/tickets/" + Integer.MAX_VALUE, String.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
        assertEquals("Ticket not found.", response.getBody(), "Response has correct error message");
    }
    
    
    public Date getCurrentDate() {
      return new Date(System.currentTimeMillis());
    }
}

class TicketDto {
    private int ticketId;
    private Date ticketDate;
    private double price;
    // Need default constructor so that Jackson can instantiate the object
    public TicketDto() {}

    public TicketDto(Date ticketDate, double price) {
      this.ticketDate = ticketDate;
      this.price = price;
    }
    
    public Date getTicketDate() {
      return ticketDate;
    }
    
    public double getPrice() {
      return price;
    }
    
    public int getTicketId() {
      return ticketId;
    }
    

}


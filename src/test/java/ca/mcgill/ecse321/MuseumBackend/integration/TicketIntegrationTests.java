package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
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
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
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
  private CustomerRepository customerRepo;

  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    ticketRepo.deleteAll();
    customerRepo.deleteAll();
  }

  @Test
  public void testCreateAndGetTicket() {
    int id = testCreateTicket();
    testGetTicket(id);
  }


  private int testCreateTicket() {

    ResponseEntity<TicketResponseDto> response = client.postForEntity("/tickets", new TicketRequestDto(1, LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(1, response.getBody().getTicketId(), "Response has correct ID");
    assertEquals(10.00, response.getBody().getPrice(), "Response has correct price");
    assertEquals(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),response.getBody().getTicketDate(), "Response has correct date");
    return response.getBody().getTicketId();
  }

  private void testGetTicket(int id) {
    ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + id, TicketResponseDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(id, response.getBody().getTicketId(), "Response has correct ID");
    assertEquals(10.00, response.getBody().getPrice(), "Response has correct price");
    assertEquals(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0), response.getBody().getTicketDate(), "Response has correct date");
  }

  @Test
  public void testCreateInvalidTicket() {
    ResponseEntity<TicketResponseDto> response = client.postForEntity("/tickets", LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0), TicketResponseDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    assertEquals("The ticket to be created contains invalid data.", response.getBody(), "Response has correct error message");
  }

  @Test
  public void testGetInvalidTicket() {
    ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + 999999999, TicketResponseDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    assertEquals("Ticket not found.", response.getBody(), "Response has correct error message");
  }
}

//
//class TicketDto {
//    private int ticketId;
//    private Date ticketDate;
//    private double price;
//    // Need default constructor so that Jackson can instantiate the object
//    public TicketDto() {}
//
//    public TicketDto(int ticketId,Date ticketDate, double price) {
//      this.ticketDate = ticketDate;
//      this.price = price;
//    }
//    
//    public Date getTicketDate() {
//      return ticketDate;
//    }
//    
//    public double getPrice() {
//      return price;
//    }
//    
//    public int getTicketId() {
//      return ticketId;
//    }
//}


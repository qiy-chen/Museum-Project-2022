package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
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
  public void testGetInvalidTicketById() {
    ResponseEntity<String> response = client.getForEntity("/tickets/" + 999999999, String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    assertEquals("Ticket not found.", response.getBody(), "Response has correct error message");
  }
  
  @Test
  public void testGetAllTickets() {
    client.postForEntity("/tickets", new TicketRequestDto(1, LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);
    client.postForEntity("/tickets", new TicketRequestDto(2, LocalDateTime.of(2000,Month.JANUARY, 5, 0, 0, 0),12.00), TicketResponseDto.class);
    ResponseEntity<List<TicketResponseDto>> response = client.exchange("/tickets",HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketResponseDto>>() {});
    List<TicketResponseDto> responseList = response.getBody();
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(2, responseList.size(), "Response has correct number of tickets");
    assertEquals(1, responseList.get(0).getTicketId(), "Response has correct ID for ticket 1");
    assertEquals(10.00, responseList.get(0).getPrice(), "Response has correct price for ticket 1");
    assertEquals(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0), responseList.get(0).getTicketDate(), "Response has correct date for ticket 1");
  }

  @Test
  public void testCreateInvalidTicket() {
    ResponseEntity<String> response = client.postForEntity("/tickets", new TicketRequestDto(1,LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),-1), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    assertEquals("The ticket to be created contains invalid data.", response.getBody(), "Response has correct error message");
  }
  @Test
  public void testCreateInvalidTicket2() {
    ResponseEntity<String> response = client.postForEntity("/tickets", new TicketRequestDto(1,null,10.00), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    assertEquals("The ticket to be created contains invalid data.", response.getBody(), "Response has correct error message");
  }
  @Test
  public void testCreateExistingTicket() {
    client.postForEntity("/tickets", new TicketRequestDto(1, LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);
    ResponseEntity<String> response = client.postForEntity("/tickets", new TicketRequestDto(1, LocalDateTime.of(2000,Month.JANUARY, 5, 0, 0, 0),12.00), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode(), "Response has correct status");
    assertEquals("A ticket with the given id already exists.", response.getBody(), "Response has correct error message");
  }
  @Test
  public void testReplaceTicket() {
    int firstId = 1;
    int secondId = 2;
    client.postForEntity("/tickets", new TicketRequestDto(firstId, LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(secondId, LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0),12.00));
    ResponseEntity<TicketResponseDto> response1 = client.exchange("/tickets/"+firstId, HttpMethod.PUT, entity, TicketResponseDto.class);
    
    ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + firstId, TicketResponseDto.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.OK, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals(firstId, response1.getBody().getTicketId(), "Response has correct ID");
    assertEquals(12.00, response1.getBody().getPrice(), "Response has correct price");
    assertEquals(LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0), response1.getBody().getTicketDate(), "Response has correct date");
    
    //First ticket doesn't exists anymore
    ResponseEntity<TicketResponseDto> response2 = client.getForEntity("/tickets/" + "2", TicketResponseDto.class);
    assertNull(response2);
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


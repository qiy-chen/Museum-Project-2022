package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.PersonDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class TicketIntegrationTests {

  @Autowired
  private TestRestTemplate client;

  @Autowired
  private TicketRepository ticketRepo;
  @Autowired
  private CustomerRepository customerRepo;
  @Autowired
  private PersonRepository personRepo;
  

  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    ticketRepo.deleteAll();
    customerRepo.deleteAll();
    personRepo.deleteAll();
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
    
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(secondId, LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0),12.00),mHttpHeaders);
    
    ResponseEntity<TicketResponseDto> response1 = client.exchange("/tickets/"+firstId, HttpMethod.PUT, entity, TicketResponseDto.class);
    
    //ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + firstId, TicketResponseDto.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.OK, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals(firstId, response1.getBody().getTicketId(), "Response has correct ID");
    assertEquals(12.00, response1.getBody().getPrice(), "Response has correct price");
    assertEquals(LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0), response1.getBody().getTicketDate(), "Response has correct date");

    
  }
  //Test replace a non-existent ticket
  @Test
  public void testReplaceTicket2() {
    int firstId = 10;

    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(firstId, LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0),12.00),mHttpHeaders);
    
    ResponseEntity<TicketResponseDto> response1 = client.exchange("/tickets/"+firstId, HttpMethod.PUT, entity, TicketResponseDto.class);
    
    //ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + firstId, TicketResponseDto.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.OK, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals(firstId, response1.getBody().getTicketId(), "Response has correct ID");
    assertEquals(12.00, response1.getBody().getPrice(), "Response has correct price");
    assertEquals(LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0), response1.getBody().getTicketDate(), "Response has correct date");

    
  }
  
  @Test
  public void testReplaceInvalidTicket() {
    int firstId = 10;

    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(firstId, LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0),-9),mHttpHeaders);
    
    ResponseEntity<String> response1 = client.exchange("/tickets/"+firstId, HttpMethod.PUT, entity, String.class);
    
    //ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + firstId, TicketResponseDto.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals("The ticket to be created contains invalid data.", response1.getBody(), "Response has correct error message");
    
  }
  @Test
  public void testReplaceInvalidTicket2() {
    int firstId = 10;

    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(firstId, null,12.00),mHttpHeaders);
    
    ResponseEntity<String> response1 = client.exchange("/tickets/"+firstId, HttpMethod.PUT, entity, String.class);
    
    //ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + firstId, TicketResponseDto.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals("The ticket to be created contains invalid data.", response1.getBody(), "Response has correct error message");
    
  }
  @Test
  public void testdeleteTicket() {
    int id = 1;
    client.postForEntity("/tickets", new TicketRequestDto(id, LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);

    ResponseEntity<TicketResponseDto> response1 = client.exchange("/tickets/"+id, HttpMethod.DELETE, null, TicketResponseDto.class);
    
    assertNotNull(response1);
    assertEquals(HttpStatus.OK, response1.getStatusCode(), "Response has correct status");
  }
  
  @Test
  public void purchaseTicket() {
    int customerId = 10;
    String personEmail = "qi@mail.com";
    int id = 1;
    
    //Creating a person directly, since person controller does not exist as for now
    Person person = new Person();
    person.setEmail(personEmail);
    person.setName("Qi Yuan");
    person.setPassword("12345");
    personRepo.save(person);
    
    PersonDto persondto = new PersonDto(person);
    //ResponseEntity<String> personResponse = client.postForEntity("/customer", new CustomerRequestDto("qi@mail.com",customerId, new int[]{1}), String.class);
    //Ensure the person is correctly added
    //assertEquals(HttpStatus.CREATED, personResponse.getStatusCode(), "Response has correct status");
    
    CustomerRequestDto customerDto = new CustomerRequestDto(persondto,customerId, new int[]{});
    
    ResponseEntity<String> customerResponse = client.postForEntity("/customer", customerDto, String.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse.getStatusCode(), "Response has correct status");
    ResponseEntity<String> response1 = client.postForEntity("/customers/"+customerId, new TicketRequestDto(customerId, LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), String.class);
    //Ensure the ticket is correctly purchased
    assertEquals(HttpStatus.CREATED, response1.getStatusCode(), "Response has correct status");
    ResponseEntity<List<TicketResponseDto>> response = client.exchange("/customers/"+customerId,HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketResponseDto>>() {});
    List<TicketResponseDto> responseList = response.getBody();
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(1, responseList.size(), "Response has correct number of tickets");
    assertEquals(id, responseList.get(0).getTicketId(), "Response has correct ID for ticket 1");
    assertEquals(10.00, responseList.get(0).getPrice(), "Response has correct price for ticket 1");
    assertEquals(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0), responseList.get(0).getTicketDate(), "Response has correct date for ticket 1");
  }
  
  @Test
  public void browseTicket() {
    int customerId = 10;
    int id = 1;
    int id2 = 2;

    
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


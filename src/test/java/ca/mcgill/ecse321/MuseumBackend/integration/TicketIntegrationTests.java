package ca.mcgill.ecse321.MuseumBackend.integration;

import ca.mcgill.ecse321.MuseumBackend.dto.*;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    ResponseEntity<TicketResponseDto> response = client.postForEntity("/tickets", new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
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
    ResponseEntity<TicketResponseDto> responseTicketOne = client.postForEntity("/tickets", new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);
    int ticketOneId = responseTicketOne.getBody().getTicketId();
    client.postForEntity("/tickets", new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 5, 0, 0, 0),12.00), TicketResponseDto.class);
    ResponseEntity<List<TicketResponseDto>> response = client.exchange("/tickets",HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketResponseDto>>() {});
    List<TicketResponseDto> responseList = response.getBody();
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(2, responseList.size(), "Response has correct number of tickets");
    assertEquals(ticketOneId, responseList.get(0).getTicketId(), "Response has correct ID for ticket 1");
    assertEquals(10.00, responseList.get(0).getPrice(), "Response has correct price for ticket 1");
    assertEquals(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0), responseList.get(0).getTicketDate(), "Response has correct date for ticket 1");
  }

  @Test
  public void testCreateInvalidTicket() {
    ResponseEntity<String> response = client.postForEntity("/tickets", new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),-1), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    assertEquals("The ticket to be created contains invalid data.", response.getBody(), "Response has correct error message");
  }
  @Test
  public void testCreateInvalidTicket2() {
    ResponseEntity<String> response = client.postForEntity("/tickets", new TicketRequestDto(null,10.00), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    assertEquals("The ticket to be created contains invalid data.", response.getBody(), "Response has correct error message");
  }

  @Test
  public void testReplaceTicket() {
    ResponseEntity<TicketResponseDto> responseTicketOne = client.postForEntity("/tickets", new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);
    int ticketId = responseTicketOne.getBody().getTicketId();
    
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0),12.00),mHttpHeaders);
    
    ResponseEntity<TicketResponseDto> response1 = client.exchange("/tickets/"+ticketId, HttpMethod.PUT, entity, TicketResponseDto.class);
    
    //ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + firstId, TicketResponseDto.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.OK, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals(ticketId, response1.getBody().getTicketId(), "Response has correct ID");
    assertEquals(12.00, response1.getBody().getPrice(), "Response has correct price");
    assertEquals(LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0), response1.getBody().getTicketDate(), "Response has correct date");

    
  }
  //Test replace a non-existent ticket
  @Test
  public void testReplaceTicket2() {
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0),12.00),mHttpHeaders);
    
    ResponseEntity<TicketResponseDto> response1 = client.exchange("/tickets/"+9999999, HttpMethod.PUT, entity, TicketResponseDto.class);
    
    
    //ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + firstId, TicketResponseDto.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.OK, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals(12.00, response1.getBody().getPrice(), "Response has correct price");
    assertEquals(LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0), response1.getBody().getTicketDate(), "Response has correct date");

    
  }
  
  @Test
  public void testReplaceInvalidTicket() {

    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(LocalDateTime.of(2022,Month.JANUARY, 1, 0, 0, 0),-9),mHttpHeaders);

    ResponseEntity<String> response1 = client.exchange("/tickets/"+9999999, HttpMethod.PUT, entity, String.class);
    
    //ResponseEntity<TicketResponseDto> response = client.getForEntity("/tickets/" + firstId, TicketResponseDto.class);
    assertNotNull(response1);
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals("The ticket to be created contains invalid data.", response1.getBody(), "Response has correct error message");
    
  }
  @Test
  public void testReplaceInvalidTicket2() {
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<TicketRequestDto> entity = new HttpEntity<TicketRequestDto>(new TicketRequestDto(null,12.00),mHttpHeaders);
    
    ResponseEntity<String> response1 = client.exchange("/tickets/"+9999999, HttpMethod.PUT, entity, String.class);
    

    assertNotNull(response1);
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");
    assertNotNull(response1.getBody(), "Response has body");
    assertEquals("The ticket to be created contains invalid data.", response1.getBody(), "Response has correct error message");
    
  }
  @Test
  public void testDeleteTicket() {
    ResponseEntity<TicketResponseDto> responsePost = client.postForEntity("/tickets", new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);

    int id = responsePost.getBody().getTicketId();
    assertNotNull(responsePost);
    assertEquals(HttpStatus.CREATED, responsePost.getStatusCode(), "Response has correct status");
    
    ResponseEntity<TicketResponseDto> response1 = client.exchange("/tickets/"+id, HttpMethod.DELETE, null, TicketResponseDto.class);

    assertNotNull(response1);
    assertEquals(HttpStatus.OK, response1.getStatusCode(), "Response has correct status");
    
    //Check if ticket is now present
    ResponseEntity<String> responseGet = client.getForEntity("/tickets/" + id, String.class);
    assertNotNull(responseGet);
    assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode(), "Response has correct status");
  }
  
  @Test
  public void purchaseAndBrowseTickets() {
    
    String personEmail = "qi@mail.com";
    
    //Creating a person directly
    Person person = new Person();
    person.setEmail(personEmail);
    person.setName("Qi Yuan");
    person.setPassword("12345");
    person = personRepo.save(person);

    String personEmail2 = "random@mail.com";
    Person person2 = new Person();
    person2.setEmail(personEmail2);
    person2.setName("random");
    person2.setPassword("random1");
    person2 = personRepo.save(person2);

    CustomerRequestDto customerDto = new CustomerRequestDto();
    customerDto.setEmail(personEmail);
    ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customer", customerDto, CustomerResponseDto.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse.getStatusCode(), "Response has correct status");
    int customerId = customerResponse.getBody().getId();
    
    CustomerRequestDto customerDto2 = new CustomerRequestDto();
    customerDto2.setEmail(personEmail2);
    ResponseEntity<CustomerResponseDto> customerResponse2 = client.postForEntity("/customer", customerDto2, CustomerResponseDto.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse2.getStatusCode(), "Response has correct status");
    int customerId2 = customerResponse2.getBody().getId();
    
    //Purchase some tickets
    ResponseEntity<TicketResponseDto> response1 = client.postForEntity("/customers/"+customerId, new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), TicketResponseDto.class);
    ResponseEntity<TicketResponseDto> response2 = client.postForEntity("/customers/"+customerId, new TicketRequestDto(LocalDateTime.of(2020,Month.JANUARY, 1, 0, 0, 0),8.00), TicketResponseDto.class);
    ResponseEntity<TicketResponseDto> response3 = client.postForEntity("/customers/"+customerId2, new TicketRequestDto(LocalDateTime.of(3000,Month.JANUARY, 1, 0, 0, 0),1.00), TicketResponseDto.class);
    //Ensure the ticket is correctly purchased
    assertEquals(HttpStatus.CREATED, response1.getStatusCode(), "Response has correct status");
    assertEquals(HttpStatus.CREATED, response2.getStatusCode(), "Response has correct status");
    assertEquals(HttpStatus.CREATED, response3.getStatusCode(), "Response has correct status");
    
    ResponseEntity<List<TicketResponseDto>> response = client.exchange("/customers/"+customerId,HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketResponseDto>>() {});
    List<TicketResponseDto> responseList = response.getBody();
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(2, responseList.size(), "Response has correct number of tickets");
    assertEquals(10.00, responseList.get(0).getPrice(), "Response has correct price for ticket 1");
    assertEquals(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0), responseList.get(0).getTicketDate(), "Response has correct date for ticket 1");
  }
  
  @Test
  public void purchaseInvalidTicket() {
    
    String personEmail = "qi@mail.com";
    
    //Creating a person directly
    Person person = new Person();
    person.setEmail(personEmail);
    person.setName("Qi Yuan");
    person.setPassword("12345");
    person = personRepo.save(person);

    CustomerRequestDto customerDto = new CustomerRequestDto();
    customerDto.setEmail(personEmail);
    ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customer", customerDto, CustomerResponseDto.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse.getStatusCode(), "Response has correct status");
    int customerId = customerResponse.getBody().getId();
    
    ResponseEntity<String> response1 = client.postForEntity("/customers/"+customerId, new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),-9.00), String.class);
    
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");
    
    //Browse customer tickets
    ResponseEntity<List<TicketResponseDto>> response = client.exchange("/customers/"+customerId,HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketResponseDto>>() {});
    List<TicketResponseDto> responseList = response.getBody();
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(0, responseList.size(), "Response has correct number of tickets");
  }
  
  @Test
  public void purchaseTicketMissingCustomer() {
    
   int customerId = 9999;
    
    ResponseEntity<String> response1 = client.postForEntity("/customers/"+customerId, new TicketRequestDto(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),10.00), String.class);
    
    assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode(), "Response has correct status");

  }
  @Test
  public void purchaseInvalidTicket2() {
    
    String personEmail = "qi@mail.com";
    
    //Creating a person directly
    Person person = new Person();
    person.setEmail(personEmail);
    person.setName("Qi Yuan");
    person.setPassword("12345");
    person = personRepo.save(person);

    CustomerRequestDto customerDto = new CustomerRequestDto();
    customerDto.setEmail(personEmail);
    ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customer", customerDto, CustomerResponseDto.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse.getStatusCode(), "Response has correct status");
    int customerId = customerResponse.getBody().getId();
    
    ResponseEntity<String> response1 = client.postForEntity("/customers/"+customerId, new TicketRequestDto(null,10.00), String.class);
    
    assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode(), "Response has correct status");
    
    //Browse customer tickets
    ResponseEntity<List<TicketResponseDto>> response = client.exchange("/customers/"+customerId,HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketResponseDto>>() {});
    List<TicketResponseDto> responseList = response.getBody();
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(0, responseList.size(), "Response has correct number of tickets");
  }
  
  
  @Test
  public void cancelTicket() {
    
    String personEmail = "qi@mail.com";
    
    //Creating a person directly
    Person person = new Person();
    person.setEmail(personEmail);
    person.setName("Qi Yuan");
    person.setPassword("12345");
    person = personRepo.save(person);

    CustomerRequestDto customerDto = new CustomerRequestDto();
    customerDto.setEmail(personEmail);
    ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customer", customerDto, CustomerResponseDto.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse.getStatusCode(), "Response has correct status");
    int customerId = customerResponse.getBody().getId();
    
    //Ticket date is 4 days after now
    ResponseEntity<TicketResponseDto> responsePost = client.postForEntity("/customers/"+customerId, new TicketRequestDto(LocalDateTime.now().plus(Duration.ofDays(4)),10.00), TicketResponseDto.class);
    
    assertEquals(HttpStatus.CREATED, responsePost.getStatusCode(), "Response has correct status");
    int ticketId = responsePost.getBody().getTicketId();
    
    //Create request body
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<IdRequestDto> entity = new HttpEntity<IdRequestDto>(new IdRequestDto(ticketId),mHttpHeaders);
    //Cancel ticket
    ResponseEntity<TicketResponseDto> responseDelete = client.exchange("/customers/"+customerId, HttpMethod.DELETE, entity, TicketResponseDto.class);

    assertNotNull(responseDelete);
    assertEquals(HttpStatus.OK, responseDelete.getStatusCode(), "Response has correct status");
    
    //Browse customer tickets
    ResponseEntity<List<TicketResponseDto>> response = client.exchange("/customers/"+customerId,HttpMethod.GET, null, new ParameterizedTypeReference<List<TicketResponseDto>>() {});
    List<TicketResponseDto> responseList = response.getBody();

    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(0, responseList.size(), "Response has correct number of tickets");
  }
  
  @Test
  public void cancelTicketTooLate() {
    
    String personEmail = "qi@mail.com";
    
    //Creating a person directly
    Person person = new Person();
    person.setEmail(personEmail);
    person.setName("Qi Yuan");
    person.setPassword("12345");
    person = personRepo.save(person);

    CustomerRequestDto customerDto = new CustomerRequestDto();
    customerDto.setEmail(personEmail);
    ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customer", customerDto, CustomerResponseDto.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse.getStatusCode(), "Response has correct status");
    int customerId = customerResponse.getBody().getId();
    
    //Ticket date is 2 days after now (too late for cancel)
    ResponseEntity<TicketResponseDto> responsePost = client.postForEntity("/customers/"+customerId, new TicketRequestDto(LocalDateTime.now().plus(Duration.ofDays(2)),10.00), TicketResponseDto.class);
    
    assertEquals(HttpStatus.CREATED, responsePost.getStatusCode(), "Response has correct status");
    int ticketId = responsePost.getBody().getTicketId();
    
    //Create request body
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<IdRequestDto> entity = new HttpEntity<IdRequestDto>(new IdRequestDto(ticketId),mHttpHeaders);
    //Cancel ticket
    ResponseEntity<String> responseDelete = client.exchange("/customers/"+customerId, HttpMethod.DELETE, entity, String.class);

    assertNotNull(responseDelete);
    assertEquals(HttpStatus.FORBIDDEN, responseDelete.getStatusCode(), "Response has correct status");

  }
  @Test
  public void cancelTicketWrongCustomer() {
    
    String personEmail = "qi@mail.com";
    
    //Creating a person directly
    Person person = new Person();
    person.setEmail(personEmail);
    person.setName("Qi Yuan");
    person.setPassword("12345");
    person = personRepo.save(person);
    
    String personEmail2 = "random@mail.com";
    
    //Creating a person directly
    Person person2 = new Person();
    person2.setEmail(personEmail2);
    person2.setName("Random");
    person2.setPassword("random");
    person2 = personRepo.save(person2);

    CustomerRequestDto customerDto = new CustomerRequestDto();
    customerDto.setEmail(personEmail);
    ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customer", customerDto, CustomerResponseDto.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse.getStatusCode(), "Response has correct status");
    int customerId = customerResponse.getBody().getId();
    
    CustomerRequestDto customerDto2 = new CustomerRequestDto();
    customerDto2.setEmail(personEmail2);
    ResponseEntity<CustomerResponseDto> customerResponse2 = client.postForEntity("/customer", customerDto2, CustomerResponseDto.class);
    //Ensure the customer is correctly added
    assertEquals(HttpStatus.CREATED, customerResponse2.getStatusCode(), "Response has correct status");
    int customerId2 = customerResponse2.getBody().getId();
    
    //Ticket date is 4 days after now
    ResponseEntity<TicketResponseDto> responsePost = client.postForEntity("/customers/"+customerId, new TicketRequestDto(LocalDateTime.now().plus(Duration.ofDays(4)),10.00), TicketResponseDto.class);
    
    assertEquals(HttpStatus.CREATED, responsePost.getStatusCode(), "Response has correct status");
    int ticketId = responsePost.getBody().getTicketId();
    
    //Create request body
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<IdRequestDto> entity = new HttpEntity<IdRequestDto>(new IdRequestDto(ticketId),mHttpHeaders);
    //Cancel ticket using wrong customer
    ResponseEntity<String> responseDelete = client.exchange("/customers/"+customerId2, HttpMethod.DELETE, entity, String.class);

    assertNotNull(responseDelete);
    assertEquals(HttpStatus.FORBIDDEN, responseDelete.getStatusCode(), "Response has correct status");

  }

}



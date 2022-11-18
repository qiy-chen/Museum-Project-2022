package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoanIntegrationTest {
  @Autowired
  private TestRestTemplate client;
  
  @Autowired
  private ArtworkRepository artworkRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private LoanRepository loanRepository;
  @Autowired
  private MuseumRepository museumRepository;
  
  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    artworkRepository.deleteAll();
    customerRepository.deleteAll();
    loanRepository.deleteAll();
    museumRepository.deleteAll();
  }
  
  @Test
  public void testGetAndCreateLoan() {
    //int id= testCreateLoan();
    //testGetLoanById(id);
    testCreateLoan();
  }
  
  private int testCreateLoan() {
    Museum museum = new Museum();
    int museumId = museum.getMuseumId();
    
    museumRepository.save(museum);
    
    Artwork artwork = new Artwork();
    int artworkId = artwork.getArtworkId();
    artwork.setMuseum(museum);
    artworkRepository.save(artwork);
    
    Customer customer = new Customer();
    int customerId = customer.getPersonRoleId();
    
    customerRepository.save(customer);
    
    LoanRequestDto dto = new LoanRequestDto(0,2,2,customerId,artworkId,museumId);
    
    ResponseEntity<LoanResponseDto> response = client.postForEntity("/loans", dto, LoanResponseDto.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(artworkId, response.getBody().getArtworkId());
    assertEquals(customerId, response.getBody().getCustomerId());
    assertEquals(2, response.getBody().getNumOfDays());
    assertEquals(2, response.getBody().getRentalFee());
    assertEquals(museumId,response.getBody().getMuseumId());
    assertNull(response.getBody().getStartDate());
    return response.getBody().getLoanId();
  }
  
private void testGetLoanById(int id) {
  ResponseEntity<LoanResponseDto> response = client.getForEntity("/loans/" + id, LoanResponseDto.class);
  assertNotNull(response);
  assertEquals(HttpStatus.OK, response.getStatusCode());
  assertNotNull(response.getBody());
  assertTrue(response.getBody().getLoanId() == id);
  assertEquals(2, response.getBody().getArtworkId());
  assertEquals(3, response.getBody().getCustomerId());
  assertEquals(10, response.getBody().getNumOfDays());
  assertEquals(20.0, response.getBody().getRentalFee());
  assertEquals(5,response.getBody().getMuseumId());
  assertNull(response.getBody().getStartDate());
    
  }
  
}

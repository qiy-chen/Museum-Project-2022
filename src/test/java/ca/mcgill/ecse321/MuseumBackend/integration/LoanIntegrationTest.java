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
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoanIntegrationTest {
  @Autowired
  private TestRestTemplate client;
  
  @Autowired
  private LoanRepository loanRepo;
  @Autowired
  private CustomerRepository customerRepo;
  @Autowired
  private ArtworkRepository artRepo;
  @Autowired
  private MuseumRepository museumRepo;
  
  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    loanRepo.deleteAll();
    customerRepo.deleteAll();
    artRepo.deleteAll();
    museumRepo.deleteAll();
  }
  
 @Test
  public void testCreateAndGetLoan() {
    int id = testCreateLoan();
    testGetLoan(id);
  }
  
  public int testCreateLoan() {
    Artwork artwork = new Artwork();
    artwork.setArtworkId(2);
    artRepo.save(artwork);
    
    Customer customer = new Customer();
    customer.setPersonRoleId(3);
    customerRepo.save(customer);
    
    Museum museum = new Museum();
    museum.setMuseumId(2);
    museumRepo.save(museum);
     
    
    LoanRequestDto dto = new LoanRequestDto(2,3,10,20.0,2);
    
    ResponseEntity<LoanResponseDto> response = client.postForEntity("/loan", dto, LoanResponseDto.class);
    
  
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().getArtworkId());
    assertEquals(3, response.getBody().getCustomerId());
    assertEquals(10, response.getBody().getNumOfDays());
    assertEquals(20.0, response.getBody().getRentalFee());
    assertEquals(2,response.getBody().getMuseumId());
    assertNull(response.getBody().getStartDate());
    return response.getBody().getId();
  }
  
  private void testGetLoan(int id) {
    ResponseEntity<LoanResponseDto> response = client.getForEntity("/loan/" + id, LoanResponseDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getId() == id);
    assertEquals(2, response.getBody().getArtworkId());
    assertEquals(3, response.getBody().getCustomerId());
    assertEquals(10, response.getBody().getNumOfDays());
    assertEquals(20.0, response.getBody().getRentalFee());
    assertEquals(2,response.getBody().getMuseumId());
    assertNull(response.getBody().getStartDate());
    
  }
}

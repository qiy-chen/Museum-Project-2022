package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoanIntegrationTest {
  @Autowired
  private TestRestTemplate client;
  
  @Autowired
  private LoanRepository loanRepo;
  
  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    loanRepo.deleteAll();
  }
  
  @Test
  public void testCreateAndGetLoan() {
    int id = testCreateLoan();
    testGetLoan(id);
  }
  private int testCreateLoan() {
    ResponseEntity<LoanDto> response = client.postForEntity("/loan", new LoanDto(123), LoanDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(123, response.getBody().getId());
    assertTrue(response.getBody().getId() > 0);
    
    return response.getBody().getId();
  }
  
  private void testGetLoan(int id) {
    ResponseEntity<LoanDto> response = client.getForEntity("/loan/"+id, LoanDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(123, response.getBody().getId());
    assertTrue(response.getBody().getId() == 123);
  }
}
class LoanDto{
  private int id;
  
  public LoanDto() {}
  public LoanDto(int id) {
    this.id = id;
  }
  public int getId() {
    return this.id;
  }
}

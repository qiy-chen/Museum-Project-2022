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
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
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
    //Loan loan = new Loan();
    //int loanId = 321;
    //loan.setLoanId(loanId);
    //loanRepo.save(loan);
    
    ResponseEntity<LoanDto> response = client.postForEntity("/loan", new LoanDto(123,567), LoanDto.class);
    assertNotNull(response);
    System.out.println(response.getBody());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    //assertEquals(123, response.getBody().getcustomerid());
    assertTrue(response.getBody().getcustomerid() == 123);
    return response.getBody().getcustomerid();
  }
  
  private void testGetLoan(int id) {
    ResponseEntity<LoanDto> response = client.getForEntity("/loan/" + id, LoanDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    //assertEquals(123, response.getBody().getId());
    assertTrue(response.getBody().getcustomerid() == 123);
  }
}
class LoanDto{
  private int customerid;
  private int artworkid;
  
  public LoanDto() {}
  public LoanDto(int customerid, int artworkid) {
    this.customerid = customerid;
    this.artworkid = artworkid;
  }
  public int getcustomerid() {
    return this.customerid;
  }
  public int getartworkrid() {
    return this.artworkid;
  }
}

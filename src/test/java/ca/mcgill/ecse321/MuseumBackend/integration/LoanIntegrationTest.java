package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoanIntegrationTest {
  @Autowired
  private TestRestTemplate client;
  
  @Autowired
  private LoanRepository loanRepo;
  @Autowired
  private CustomerRepository cusRepo;
  @Autowired
  private ArtworkRepository artRepo;
  
  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    loanRepo.deleteAll();
    cusRepo.deleteAll();
    artRepo.deleteAll();
  }
  
 /* @Test
  public void testCreateAndGetLoan() {
    int id = testCreateLoan();
    testGetLoan(id);
  }*/
  @Test
  public  void testCreateLoan() {
    Artwork artwork = new Artwork();
    Customer customer = new Customer();
    int numOfDays = 3;
    Date startDate = new Date(4);
    Date endDate = new Date(7);
    double rentalFee = 20.00;
    artRepo.save(artwork);
    cusRepo.save(customer);
    
    ResponseEntity<LoanDto> response = client.postForEntity("/loan", new LoanDto(artwork, customer, numOfDays, startDate, endDate, rentalFee), LoanDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(artwork, response.getBody().artwork);
    assertEquals(customer, response.getBody().customer);
    assertEquals(numOfDays, response.getBody().numOfDays);
    assertEquals(startDate, response.getBody().startDate);
    assertEquals(endDate, response.getBody().endDate);
    assertEquals(rentalFee, response.getBody().rentalFee);
    
    //return response.getBody().id;
  }
  
  private void testGetLoan(int id) {
    ResponseEntity<LoanDto> response = client.getForEntity("/loan/" + id, LoanDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().id == id);
  }
}
class LoanDto{
  int id;
  Artwork artwork;
  Customer customer;
  int numOfDays;
  Date startDate;
  Date endDate;
  double rentalFee;
  LoanStatus status;
  public LoanDto() {}
  public LoanDto(Artwork artwork, Customer customer, int nod, Date sd, Date ed, double rf) {
    this.artwork = artwork;
    this.customer = customer;
    this.numOfDays = nod;
    this.startDate = sd;
    this.endDate = ed;
    this.rentalFee = rf;
    this.status = LoanStatus.Requested;
  }
}

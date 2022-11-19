package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import ca.mcgill.ecse321.MuseumBackend.dto.LoanRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
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
  /**
   * @author alextsah and emmakawczynski
   * 
   */
  @Test
  public void testGetAndCreateLoan() {
    int id= testCreateLoan();
    testGetLoanById(id);
    //testCreateLoan();
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
    String startDate="2015-03-31"; 
    String endDate = "2015-04-29";
    int statusNumber = 2;
    int numOfDays = 3;
    double rentalFee = 2.5;
    
    LoanRequestDto dto = new LoanRequestDto(statusNumber,startDate,endDate, numOfDays, rentalFee,customerId,artworkId,museumId);
    
    ResponseEntity<LoanResponseDto> response = client.postForEntity("/loans", dto, LoanResponseDto.class);
    startDate = "2015-03-30";
    endDate = "2015-04-28";
    
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(LoanStatus.Requested,response.getBody().getStatus());
    assertEquals(numOfDays, response.getBody().getNumOfDays());
    assertEquals(rentalFee, response.getBody().getRentalFee());
    assertEquals(artworkId, response.getBody().getArtworkId());
    assertEquals(customerId, response.getBody().getCustomerId());
    assertEquals(museumId,response.getBody().getMuseumId());
    assertNotNull(response.getBody().getStartDate());
    assertEquals(startDate, response.getBody().getStartDate().toString());
    assertEquals(endDate, response.getBody().getEndDate().toString());
    return response.getBody().getLoanId();
  }
  
  private void testGetLoanById(int id) {
    ResponseEntity<LoanResponseDto> response = client.getForEntity("/loans/" + id, LoanResponseDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getLoanId() == id);
    assertEquals(0, response.getBody().getArtworkId());
    assertEquals(0, response.getBody().getCustomerId());
    assertEquals(3, response.getBody().getNumOfDays());
    assertEquals(2.5, response.getBody().getRentalFee());
    assertEquals(0,response.getBody().getMuseumId());
    assertEquals("2015-03-30",response.getBody().getStartDate().toString());
    assertEquals("2015-04-28",response.getBody().getEndDate().toString());
    assertEquals(LoanStatus.Requested,response.getBody().getStatus());
    
  }
  @Test
  public void testCreateandDeleteLoan() {
    int id= testCreateLoan();
    testDeleteLoan(id);
  }
  private void testDeleteLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Museum museum = new Museum();
    loan.setMuseum(museum);
    Artwork artwork = new Artwork();
    loan.setArtwork(artwork);
    artwork.addLoan(loan);
    Customer customer = new Customer();
    loan.setCustomer(customer);
    customer.addLoan(loan);
    LoanRequestDto loanreq = new LoanRequestDto(1, "2015-03-31","2015-04-29", 3, 2.5, artwork.getArtworkId(), customer.getPersonRoleId(), 0);
    ResponseEntity<LoanResponseDto> response = client.postForEntity("/loans/delete/" + loanId, loanreq, LoanResponseDto.class);
    assertNotNull(response);
    assertNull(loanRepository.findLoanByLoanId(loanId));
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
 
  @Test
  public void testCreateandDenyLoan() {
    int id= testCreateLoan();
    testDenyLoan(id);
  }
  private void testDenyLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Museum museum = new Museum();
    loan.setMuseum(museum);
    Artwork artwork = new Artwork();
    loan.setArtwork(artwork);
    artwork.addLoan(loan);
    Customer customer = new Customer();
    loan.setCustomer(customer);
    customer.addLoan(loan);
    LoanRequestDto loanreq = new LoanRequestDto(2, "2015-03-31","2015-04-29", 3, 2.5, artwork.getArtworkId(), customer.getPersonRoleId(), 0);
    ResponseEntity<LoanResponseDto> response = client.postForEntity("/loans/deny/" + loanId, loanreq, LoanResponseDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(LoanStatus.Denied,response.getBody().getStatus());
  }
  
  @Test
  public void testCreateAndApproveLoan() {
    int id = testCreateLoan();
    testApproveLoan(id);
  }
  
  private void testApproveLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Museum museum = new Museum();
    loan.setMuseum(museum);
    Artwork artwork = new Artwork();
    artwork.setIsLoanable(false);
    loan.setArtwork(artwork);
    artwork.addLoan(loan);
    Customer customer = new Customer();
    loan.setCustomer(customer);
    customer.addLoan(loan);
    LoanRequestDto loanreq = new LoanRequestDto(2, "2015-03-31","2015-04-29", 3, 2.5, artwork.getArtworkId(), customer.getPersonRoleId(), 0);
    ResponseEntity<LoanResponseDto> response = client.postForEntity("/loans/approve/" + loanId, loanreq, LoanResponseDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(LoanStatus.Approved,response.getBody().getStatus());
    assertEquals(loan.getArtwork().getIsLoanable(), false);
  }
  
  @Test
  public void testCreateAndreturnArtworkandEndLoan() {
    int id = testCreateLoan2();
    returnArtworkandEndLoan(id);
  }
  
  private void returnArtworkandEndLoan(int loanId) {
    System.out.println(loanId + "WHAT");
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Museum museum = new Museum();
    loan.setMuseum(museum);
    Artwork artwork = new Artwork();
    artwork.setIsLoanable(false);
    loan.setArtwork(artwork);
    artwork.addLoan(loan);
    Customer customer = new Customer();
    loan.setCustomer(customer);
    customer.addLoan(loan);
    loan.setStatus(LoanStatus.Approved);
    LoanRequestDto loanreq = new LoanRequestDto(0, "2015-03-31","2015-04-29", 3, 2.5, artwork.getArtworkId(), customer.getPersonRoleId(), 0);
    ResponseEntity<LoanResponseDto> response = client.postForEntity("/loans/return/" + loanId, loanreq, LoanResponseDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(LoanStatus.Returned,response.getBody().getStatus());
    //assertEquals(loan.getArtwork().getIsLoanable(), false);
     
  }
  private int testCreateLoan2() {
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
    String startDate="2015-03-31"; 
    String endDate = "2015-04-29";
    int statusNumber = 0;
    int numOfDays = 3;
    double rentalFee = 2.5;
    
    LoanRequestDto dto = new LoanRequestDto(statusNumber,startDate,endDate, numOfDays, rentalFee,customerId,artworkId,museumId);
    
    ResponseEntity<LoanResponseDto> response = client.postForEntity("/loans", dto, LoanResponseDto.class);
    startDate = "2015-03-30";
    endDate = "2015-04-28";
    
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(LoanStatus.Approved,response.getBody().getStatus());
    assertEquals(numOfDays, response.getBody().getNumOfDays());
    assertEquals(rentalFee, response.getBody().getRentalFee());
    assertEquals(artworkId, response.getBody().getArtworkId());
    assertEquals(customerId, response.getBody().getCustomerId());
    assertEquals(museumId,response.getBody().getMuseumId());
    assertNotNull(response.getBody().getStartDate());
    assertEquals(startDate, response.getBody().getStartDate().toString());
    assertEquals(endDate, response.getBody().getEndDate().toString());
    return response.getBody().getLoanId();
  }
}

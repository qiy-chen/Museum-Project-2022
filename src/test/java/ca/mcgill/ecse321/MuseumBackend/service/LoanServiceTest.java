package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
  
  @Mock
  private LoanRepository loanRepository;
  @Mock
  private CustomerRepository customerRepository;
  @Mock
  private ArtworkRepository artworkRepository;
  @Mock
  private MuseumRepository museumRepository; 
  
  @InjectMocks
  private LoanService loanService;
  
  /**
   * @author alextsah
   * 
   */
  @Test
  public void testGetArtById() {
    final Loan loan = new Loan();
    //loan.setLoanId(1);
    int loanId = loan.getLoanId();
    
    when(loanRepository.findLoanByLoanId(loanId)).thenAnswer( (InvocationOnMock invocation) -> loan);
    
    Loan loanreturn = loanService.getLoan(loanId);
    assertNotNull(loanreturn);
    assertEquals(loanId, loanreturn.getLoanId());
  }
  /**
   * @author alextsah
   * 
   */
  @Test 
  public void testCreateLoan() {
    Museum museum = new Museum();
    //museum.setMuseumId(1);
    int museumid = museum.getMuseumId();
    
    
    Artwork artwork = new Artwork();
    //artwork.setArtworkId(2);
    int artworkid = artwork.getArtworkId();
    
    LoanRequestDto loanRequest = new LoanRequestDto();
    
    LoanResponseDto loan = loanService.createLoan(loanRequest);
    
    assertNotNull(loan);
    
  }
  /**
   * @author emmakawczynski
   * 
   */
  @Test
  public void testDeleteLoan() {
    Loan loan = new Loan();
    Artwork artwork = new Artwork();
    Customer customer = new Customer();
    customer.addLoan(loan);
    artwork.addLoan(loan);
    loan.setNumOfDays(12);
    loan.setArtwork(artwork);
    loan.setCustomer(customer);
    when(loanRepository.findLoanByLoanId(loan.getLoanId())).thenAnswer( (InvocationOnMock invocation) -> loan);
    loanService.deleteLoan(loan.getLoanId());
    assertTrue(customer.getLoans().size() == 0);
    assertTrue(customer.getLoans().isEmpty());
  }
  /**
   * @author emmakawczynski
   * 
   */
  @Test
  public void testApproveLoan() {
    final Loan loan = new Loan();
    Artwork artwork = new Artwork();
    artwork.setIsLoanable(true);
    loan.setArtwork(artwork);
    artwork.addLoan(loan);
    
    loan.setStatus(LoanStatus.Requested);
    when(loanRepository.findLoanByLoanId(loan.getLoanId())).thenAnswer( (InvocationOnMock invocation) -> loan);
    loanService.approveLoan(loan.getLoanId());
    assertEquals(loan.getStatus(), LoanStatus.Approved);
    assertEquals(artwork.getIsLoanable(), false);
  }
  /**
   * @author emmakawczynski
   * 
   */
  @Test 
  public void testReturnArtworkandEndLoan() {
    final Loan loan = new Loan();
    Artwork artwork = new Artwork();
    loan.setStatus(LoanStatus.Approved);
    loan.setArtwork(artwork);
    artwork.addLoan(loan);
    when(loanRepository.findLoanByLoanId(loan.getLoanId())).thenAnswer( (InvocationOnMock invocation) -> loan);
    loanService.returnArtworkandEndLoan(loan.getLoanId());
    assertEquals(loan.getStatus(), LoanStatus.Returned);
    assertEquals(artwork.getIsLoanable(), true);
  }
  /**
   * @author emmakawczynski
   * 
   */
  @Test
  public void testDenyLoan() {
    final Loan loan = new Loan();
    loan.setStatus(LoanStatus.Requested);
    when(loanRepository.findLoanByLoanId(loan.getLoanId())).thenAnswer( (InvocationOnMock invocation) -> loan);
    loanService.denyLoan(loan.getLoanId());
    assertEquals(loan.getStatus(), LoanStatus.Denied);
  }
  /**
   * @author emmakawczynski
   * 
   */
  @Test
  public void testDeleteLoanInvalid() {
    try {
      loanService.deleteLoan(390);
    }
    catch (Exception e){
      assertEquals(e.getMessage(), "Loan not found");
    }
  }
  /**
   * @author alextsah
   * 
   */
  @Test
  public void testApproveLoanInvalid() {
    try {
      loanService.approveLoan(390);
    }
    catch (Exception e){
      assertEquals(e.getMessage(), "Loan not found");
    }
  }
  /**
   * @author alextsah
   * 
   */
  @Test
  public void testApproveLoanInvalIsLonable() {
    try {
      final Loan loan = new Loan();
      Artwork artwork = new Artwork();
      artwork.setIsLoanable(false);
      loan.setArtwork(artwork);
      artwork.addLoan(loan);
      
      loan.setStatus(LoanStatus.Requested);
      when(loanRepository.findLoanByLoanId(loan.getLoanId())).thenAnswer( (InvocationOnMock invocation) -> loan);
      loanService.approveLoan(loan.getLoanId());
    }
    catch (Exception e){
      assertEquals(e.getMessage(), "Can't approve this loan");
    }
  }
}

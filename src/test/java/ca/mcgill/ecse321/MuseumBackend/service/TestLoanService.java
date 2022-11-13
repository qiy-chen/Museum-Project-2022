package ca.mcgill.ecse321.MuseumBackend.service;


import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.*;
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class TestLoanService {
  @Mock
  LoanRepository loanRepository;
  
  @InjectMocks
  LoanService loanService;
  
  @Test
  public void testGetLoanById() {
    double rentalfee = 200.0;
    Date startDate = new Date(0);
    Date endDate = new Date(2);
    int numOfDays = 20;
    LoanStatus status = LoanStatus.Requested;
    int loaniD = 123;
    Museum museum = new Museum();
    Customer customer = new Customer();
    Artwork artwork = new Artwork();
    
    Loan loan1 = new Loan(rentalfee,startDate,endDate,numOfDays,status,loaniD,museum,customer,artwork);
    when(loanRepository.findLoanByLoanId(loaniD)).thenAnswer((InvocationOnMock invocation) -> loan1);
    
    Loan loan = loanService.getLoanById(loaniD);
    
    assertNotNull(loan);
    assertEquals(loaniD, loan.getLoanId());
  }
  
  @Test
  public void testEndLoan() {
    double rentalfee = 200.0;
    Date startDate = new Date(0);
    Date endDate = new Date(2);
    int numOfDays = 20;
    LoanStatus status = LoanStatus.Approved;
    int loaniD = 1234;
    Museum museum = new Museum();
    Customer customer = new Customer();
    Artwork artwork = new Artwork();
    
    Loan loan1 = new Loan(rentalfee,startDate,endDate,numOfDays,status,loaniD,museum,customer,artwork);
    when(loanRepository.findLoanByLoanId(loaniD)).thenAnswer((InvocationOnMock invocation) -> loan1);
    
    loanService.endLoan(loaniD);
    
    assertNotNull(loan1);
    assertEquals(LoanStatus.Returned, loan1.getStatus());
    assertTrue(loan1.getArtwork().getIsLoanable());
  }
  
  @Test
  public void testGetAllLoans() {
    double rentalfee = 200.0;
    Date startDate = new Date(0);
    Date endDate = new Date(2);
    int numOfDays = 20;
    LoanStatus status = LoanStatus.Requested;
    int loaniD = 12345;
    Museum museum = new Museum();
    Customer customer = new Customer();
    Artwork artwork = new Artwork();
    
    Loan loan1 = new Loan(rentalfee,startDate,endDate,numOfDays,status,loaniD,museum,customer,artwork);
    
    int loaniD2 = 567;
    Loan loan2 = new Loan(rentalfee,startDate,endDate,numOfDays,status,loaniD2,museum,customer,artwork);
    
    ArrayList<Loan> loans = new ArrayList<Loan>();
    loans.add(loan1);
    loans.add(loan2);
    
    when(loanRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> loans);
    
    List<Loan> allloans = loanService.getAllLoans();
    
    assertNotNull(allloans);
    assertEquals(loans,allloans);
    assertEquals(loans.get(0),allloans.get(0));
    assertEquals(loans.get(1),allloans.get(1));
  }
  //emma
  @Test
  public void testCreateLoan() {
    when(loanRepository.save(any(Loan.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
    final Loan finalLoan = new Loan();
    int id = finalLoan.getLoanId();
    Loan loanReturned = loanService.createLoan(finalLoan);
    
    assertNotNull(loanReturned);
    assertEquals(id, loanReturned.getLoanId());
    verify(loanRepository, times(1)).save(loanReturned);
    
  }
  //emma
  @Test
  public void testApproveLoan() {
    double rentalfee = 200.0;
    Date startDate = new Date(0);
    Date endDate = new Date(2);
    int numOfDays = 20;
    LoanStatus status = LoanStatus.Requested;
    int loaniD = 12345;
    Museum museum = new Museum();
    Customer customer = new Customer();
    Artwork artwork = new Artwork();
    Loan loan1 = new Loan(rentalfee,startDate,endDate,numOfDays,status,loaniD,museum,customer,artwork);
    when(loanRepository.findLoanByLoanId(loaniD)).thenAnswer((InvocationOnMock invocation) -> loan1);
    
    loanService.approveLoan(loaniD);
    
    assertNotNull(loan1);
    assertEquals(LoanStatus.Approved, loan1.getStatus());
    assertTrue(loan1.getArtwork().getIsLoanable());
    
    
  }
  @Test
  public void testDenyLoan() {
    double rentalfee = 200.0;
    Date startDate = new Date(0);
    Date endDate = new Date(2);
    int numOfDays = 20;
    LoanStatus status = LoanStatus.Requested;
    int loaniD = 123456;
    Museum museum = new Museum();
    Customer customer = new Customer();
    Artwork artwork = new Artwork();
    Loan loan1 = new Loan(rentalfee,startDate,endDate,numOfDays,status,loaniD,museum,customer,artwork);
    when(loanRepository.findLoanByLoanId(loaniD)).thenAnswer((InvocationOnMock invocation) -> loan1);
    
    loanService.denyLoan(loaniD);
    
    assertNotNull(loan1);
    assertEquals(LoanStatus.Denied, loan1.getStatus());
    assertTrue(loan1.getArtwork().getIsLoanable());
  }
  @Test
  public void testGetLoansByStatus() {
    
  }
}

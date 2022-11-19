package ca.mcgill.ecse321.MuseumBackend.service;


import java.sql.Date;
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
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    
    Loan loan = loanService.getLoan(loaniD);
    
    assertNotNull(loan);
    assertEquals(loaniD, loan.getLoanId());
  }
}

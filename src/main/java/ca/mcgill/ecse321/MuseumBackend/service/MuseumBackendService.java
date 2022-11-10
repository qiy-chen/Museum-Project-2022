package ca.mcgill.ecse321.MuseumBackend.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.repository.*;
import ca.mcgill.ecse321.MuseumBackend.model.*;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;

@Service
public class MuseumBackendService {
  @Autowired
  LoanRepository loanRepository;
  @Autowired
  MuseumRepository museumRepository;
  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  ArtworkRepository artworkRepository;
  
  @Transactional
  public Loan createLoan(double loadId, Date startDate, Date endDate, int numOfDays, int loanId, Museum museum, Customer customer, Artwork artwork ) {
      Loan loan = new Loan();
      loan.setLoanId(loanId);
      loan.setStartDate(startDate);
      loan.setEndDate(endDate);
      loan.setNumOfDays(numOfDays);
      LoanStatus status = LoanStatus.Requested;
      loan.setStatus(status);
      loan.setMuseum(museum);
      loan.setArtwork(artwork);
      loan.setCustomer(customer);
      loan.setRentalFee(loanId);
      loanRepository.save(loan);
      return loan;
  }
  
  @Transactional
  public void approveLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Artwork loanArtwork = loan.getArtwork();
    if (loanArtwork.getIsLoanable()) {
      LoanStatus status = LoanStatus.Approved;
      loan.setStatus(status);
      loanArtwork.setIsLoanable(false);
    }
    else {
      LoanStatus status = LoanStatus.Denied;
      loan.setStatus(status);
      //throw exception 
    }
    
  }
  
  @Transactional
  public Loan getloan(int loanId) {
      Loan loan = loanRepository.findLoanByLoanId(loanId);
      return loan;
  }
  
  @Transactional
  public List<Loan> getAllLoans() {
      return toList(loanRepository.findAll());
  }
  
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
  }
  
}

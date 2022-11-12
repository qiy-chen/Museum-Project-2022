package ca.mcgill.ecse321.MuseumBackend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.*;
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;

@Service
public class LoanService {
  @Autowired
  LoanRepository loanRepository;
  
  @Transactional
  public Loan getLoanById(int id) {
      Loan loan = loanRepository.findLoanByLoanId(id);
      return loan;
  }
  
  @Transactional
  public Loan createLoan(Loan loan) { //emma
      loan = loanRepository.save(loan);
      return loan;
  }
  
  @Transactional
  public void approveLoan(int loanId) { //emma
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
  public void endLoan(int loanId) { //alex
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Artwork loanArtwork = loan.getArtwork();
    loan.setStatus(LoanStatus.Returned);
    loanArtwork.setIsLoanable(true);
  }
  
  @Transactional
  public Loan readLoan(int loanId) { //alex
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    return loan;
  }
  
  @Transactional
  public void denyLoan(int loanId) { //emma
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    if(loan.getStatus().equals(LoanStatus.Requested)) {
       loan.setStatus(LoanStatus.Denied);
    }
    else if (loan.getStatus().equals(LoanStatus.Approved) || loan.getStatus().equals(LoanStatus.Returned)) {
      //throw exception
    }
  }
 
  
  @Transactional
  public List<Loan> getAllLoans() { //alex
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

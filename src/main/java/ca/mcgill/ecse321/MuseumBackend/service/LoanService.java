package ca.mcgill.ecse321.MuseumBackend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.*;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;
import ca.mcgill.ecse321.MuseumBackend.Exception.LoanException;
import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
@Service
public class LoanService {
  @Autowired
  LoanRepository loanRepository;
  
  @Autowired
  CustomerRepository customerRepository;
  
  @Autowired
  ArtworkRepository artworkRepository;
  

  @Transactional
  public Loan getLoanByLoanId(int id) { //alex  
    Loan loan = loanRepository.findLoanByLoanId(id);
    if(loan == null) {
     throw new LoanException(HttpStatus.NOT_FOUND, "Ticket not found");
    }
    return loan;
  }

  @Transactional
  public Loan createLoan(Loan loan) {
    int customerid = loan.getCustomer().getPersonRoleId();
    Customer customer = customerRepository.findCustomerByPersonRoleId(customerid);
    if (customer == null) {
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Customer with given id not found.");
    }
    
    int artworkid = loan.getArtwork().getArtworkId();
    Artwork artwork = artworkRepository.findArtworkByArtworkId(artworkid);
    if (artwork == null) {
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Artwork with given id not found.");
    }
    if (artwork.isOnLoan()) {
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Artwork is already on loan.");
    }
    int numOfDays = loan.getNumOfDays();
    if (numOfDays <=0) {
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Loans must be for more that 1 day(s).");
    }
    return loanRepository.save(loan);
  }
  @Transactional
  public boolean deleteLoan(int id) { //alex
    boolean success = false;
    Loan canceledLoan = getLoanByLoanId(id);
    canceledLoan.delete();
    loanRepository.delete(canceledLoan);
    success=true;
    return success;
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
    if(loan.getStatus().equals(LoanStatus.Approved)) {
      loan.setStatus(LoanStatus.Returned);
      loanArtwork.setIsLoanable(true);
    }
  }

  @Transactional
  public void denyLoan(int loanId) { //emma
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    if(loan.getStatus().equals(LoanStatus.Requested)) {
      loan.setStatus(LoanStatus.Denied);
      loan.getArtwork().setIsLoanable(true);
    }
    else if (loan.getStatus().equals(LoanStatus.Approved) || loan.getStatus().equals(LoanStatus.Returned)) {
      //throw exception
    }
  }
  @Transactional
  public List<Loan> getLoansByStatus(LoanStatus status){ //alex
    List<Loan> loans = toList(loanRepository.findAll());
    List<Loan> statusLoans = new ArrayList<Loan>();
    for (Loan loan : loans) {
      if(loan.getStatus().equals(status)) {
        statusLoans.add(loan);
      }
    }
    return statusLoans;
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

package ca.mcgill.ecse321.MuseumBackend.service;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
  @Autowired
  LoanRepository loanRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  ArtworkRepository artworkRepository;

  @Autowired
  MuseumRepository museumRepository;

  /**
   * @author alextsah
   * @param LoanRequestDto loanRequest that contains the loan that is going to be created
   * @return LoanResponseDto returns the loan created as a loan respoonse
   */
  @Transactional
  public LoanResponseDto createLoan(LoanRequestDto loanRequest) {
    Museum museum = museumRepository.findMuseumByMuseumId(loanRequest.getMuseumId());
    Artwork artwork = artworkRepository.findArtworkByArtworkId(loanRequest.getArtworkId());
    Customer customer = customerRepository.findCustomerByPersonRoleId(loanRequest.getCustomerId());
    Loan loan = new Loan();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
    loan.setRentalFee(loanRequest.getRentalFee());
    loan.setNumOfDays(loanRequest.getNumOfDays());
    String date = loanRequest.getStartDate();
    date += " 8:00";
    LocalDateTime startDate = LocalDateTime.parse(date, formatter);
    loan.setStartDate(startDate);
    String date2 = loanRequest.getEndDate();
    date2 += " 8:00";
    LocalDateTime endDate = LocalDateTime.parse(date2,formatter);
    loan.setEndDate(endDate);
    loan.setArtwork(artwork);
    loan.setCustomer(customer);
    loan.setMuseum(museum);
    if(loanRequest.getLoanStatusAsNumber() == 0) {
      loan.setStatus(LoanStatus.Approved);
    }
    if(loanRequest.getLoanStatusAsNumber() == 1) {
      loan.setStatus(LoanStatus.Denied);
    }
    if(loanRequest.getLoanStatusAsNumber() == 2) {
      loan.setStatus(LoanStatus.Requested);
    }
    if(loanRequest.getLoanStatusAsNumber() == 3) {
      loan.setStatus(LoanStatus.Returned);
    }
    // System.out.println(loanRequest.getLoanStatusAsNumber() + "THIS IS THE IDENTIFIER ALEX");
    loanRepository.save(loan);
    return new LoanResponseDto(loan);

  }
  /**
   * @author alextsah
   * @param int loanId to find the loan in the repository
   * @return Loan returns the loan you wanted to get
   */
  @Transactional
  public Loan getLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    return loan;
  }

  /**
   * @author emmakawczynski
   * @param int loanId to find the loan from the repository
   * @return Loan returns the loan that was deleted
   */
  @Transactional
  public Loan deleteLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    System.out.println(loan + "HEY GUYS ITS ME AGAI");
    if(loan == null) {
      throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Loan not found");
    }
    Customer customer = loan.getCustomer();
    Artwork artwork = loan.getArtwork();
    customer.removeLoan(loan);
    artwork.removeLoan(loan);
    loanRepository.delete(loan);

    loan.delete();
    System.out.println("reach here");

    return loan;
  }

  /**
   * @author emmakawczynski
   * @param int loanId to find the loan from the repository
   * @return Loan returns the loan that was approved
   */
  @Transactional
  public Loan approveLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    if(loan == null) {
      throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Loan not found");
    }
    System.out.println(loan.getArtwork() + "THIS IS WRONG");
    Artwork artwork = loan.getArtwork();
    
    LoanStatus status = loan.getStatus();
    if(status.equals(LoanStatus.Requested) && artwork.getIsLoanable() == true) {
      loan.setStatus(LoanStatus.Approved);
      artwork.setIsLoanable(false);
      return loan;
    }
    else {
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Can't approve this loan");
    }
  }

  /**
   * @author emmakawczynski
   * @param int loanId to find the loan from the repository
   * @return Loan returns the loan that was approved
   */
  @Transactional
  public Loan returnArtworkandEndLoan(int loanId) {

    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Artwork artwork = loan.getArtwork();
    LoanStatus status = loan.getStatus();
    if(status.equals(LoanStatus.Approved)) {
      loan.setStatus(LoanStatus.Returned);
      //artwork.setIsLoanable(true);
      return loan;
    }
    else {
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Can't return this loan");
    }

  }
  /**
   * @author emmakawczynski
   * @param int loanId to find the loan from the repository
   * @return Loan returns the loan that was approved
   */
  @Transactional
  public Loan denyLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    System.out.println(loan.getStatus());
    LoanStatus status = loan.getStatus();
    if(status.equals(LoanStatus.Requested)) {
      loan.setStatus(LoanStatus.Denied);
      System.out.println("Alex is a stupid bitch");
      return loan;
    }
    else {
      System.out.println("HI GUYS ITS ME");
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Can't deny this loan");
    }
  }
  /**
   * @author emmakawczynski and alextsah
   * @return List<Loans> returns all the loans
   */
  @Transactional
  public List<Loan> getAllLoans() {
    return toList(loanRepository.findAll());
  }
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for(T t: iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}

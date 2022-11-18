package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  
  @Transactional
  public LoanResponseDto createLoan(LoanRequestDto loanRequest) {
    Museum museum = museumRepository.findMuseumByMuseumId(loanRequest.getMuseumId());
    Artwork artwork = artworkRepository.findArtworkByArtworkId(loanRequest.getArtworkId());
    Customer customer = customerRepository.findCustomerByPersonRoleId(loanRequest.getCustomerId());
    Loan loan = new Loan();
    loan.setRentalFee(loanRequest.getRentalFee());
    loan.setNumOfDays(loanRequest.getNumOfDays());
    loan.setArtwork(artwork);
    loan.setCustomer(customer);
    loan.setMuseum(museum);
    
    loanRepository.save(loan);
    return new LoanResponseDto(loan);
    
  }
  @Transactional
  public Loan getLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    return loan;
  }
  @Transactional
  public Loan deleteLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    if(loan == null) {
      throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Loan not found");
    }
    Artwork artwork = loan.getArtwork();
    artwork.removeLoan(loan);
    Customer customer = loan.getCustomer();
    customer.removeLoan(loan);
    artworkRepository.save(artwork);
    customerRepository.save(customer);
    loan.delete();
    loanRepository.delete(loan);
    return loan;
  }
  
  @Transactional
  public Loan approveLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
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
  @Transactional
  public Loan returnArtworkandEndLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Artwork artwork = loan.getArtwork();
    LoanStatus status = loan.getStatus();
    if(status.equals(LoanStatus.Approved)) {
      loan.setStatus(LoanStatus.Returned);
      artwork.setIsLoanable(true);
      return loan;
    }
    else {
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Can't return this loan");
    }
  }
  @Transactional
  public Loan denyLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    LoanStatus status = loan.getStatus();
    if(status.equals(LoanStatus.Requested)) {
      loan.setStatus(LoanStatus.Denied);
      return loan;
    }
    else {
      throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Can't deny this loan");
    }
  }
}

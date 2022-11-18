package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
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
  

}

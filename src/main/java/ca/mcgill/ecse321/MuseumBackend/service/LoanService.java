package ca.mcgill.ecse321.MuseumBackend.service;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
=======
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.*;
>>>>>>> origin/alex_branch_updated
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;

@Service
public class LoanService {
<<<<<<< HEAD

	@Autowired
	LoanRepository loanRepo;
	
	@Transactional
	public Loan getLoanById(int id) {
		Loan loan = loanRepo.findLoanByLoanId(id);
		if (loan == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Loan not found.");
		}
		return loan;
	}
	
	@Transactional
	public Loan createLoan(Loan loan) {
		loan = loanRepo.save(loan);
		return loan;
	}
=======
  @Autowired
  LoanRepository loanRepository;
  
  @Transactional
  public Loan getLoanById(int id) {
      Loan loan = loanRepository.findLoanByLoanId(id);
      return loan;
  }
  
  @Transactional
  public Loan createLoan(Loan loan) {
      loan = loanRepository.save(loan);
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
  public void endLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    Artwork loanArtwork = loan.getArtwork();
    loan.setStatus(LoanStatus.Returned);
    loanArtwork.setIsLoanable(true);
  }
  
  @Transactional
  public Loan readLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    return loan;
  }
  
  @Transactional
  public void denyLoan(int loanId) {
    Loan loan = loanRepository.findLoanByLoanId(loanId);
    if(loan.getStatus().equals(LoanStatus.Requested)) {
       loan.setStatus(LoanStatus.Denied);
    }
    else if (loan.getStatus().equals(LoanStatus.Approved) || loan.getStatus().equals(LoanStatus.Returned)) {
      //throw exception
    }
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
  
  
>>>>>>> origin/alex_branch_updated
}

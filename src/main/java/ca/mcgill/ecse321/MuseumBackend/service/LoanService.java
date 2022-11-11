package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
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
  public Loan createEmployee(Loan loan) {
      loan = loanRepository.save(loan);
      return loan;
  }
}

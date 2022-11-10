package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;

@Service
public class LoanService {

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
}

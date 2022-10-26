package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, String>{

	Loan FindLoanByLoanid(String loanId);
	
}

package ca.mcgill.ecse321.MuseumBackend.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, UUID>{

	Loan FindLoanById(UUID loanId);
	
}

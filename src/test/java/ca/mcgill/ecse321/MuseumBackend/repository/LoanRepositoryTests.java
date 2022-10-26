package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.MuseumBackend.model.Loan;

public class LoanRepositoryTests {

	@Autowired
	private LoanRepository loanRepository;

	@AfterEach
	public void clearDatabase() {
		loanRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadLoan() {
		// Create object
		Loan aLoan = new Loan();
		UUID aLoanId = new UUID(0,0);
		aLoan.setLoanId(aLoanId);

		// Save object
		loanRepository.save(aLoan);
		aLoanId = aLoan.getLoanId();

		// Read object from database
		aLoan = loanRepository.FindLoanById(aLoanId);

		// Assert that object has correct attributes
		assertNotNull(aLoan);
		assertEquals(aLoanId, aLoan.getLoanId());
	}
	
}

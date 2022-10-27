package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;


@SpringBootTest
public class LoanRepositoryTests {

	@Autowired
	private LoanRepository loanRepository;
	private ArtworkRepository artworkRepository;

	@AfterEach
	public void clearDatabase() {
		loanRepository.deleteAll();
		artworkRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadLoan() {
		// Create object
		Loan aLoan = new Loan();

		// Save object
		loanRepository.save(aLoan);
		int id = aLoan.getLoanId();

		// Read object from database
		aLoan = loanRepository.FindLoanById(id);

		// Assert that object has correct attributes
		assertNotNull(aLoan);
		assertEquals(id, aLoan.getLoanId());
	}
	
	@Test
	public void testLoanToArtworkReference() {
		// Create object
		Loan aLoan = new Loan();
		Artwork art = new Artwork();
		aLoan.setArtwork(art);

		// Save object
		art = artworkRepository.save(art);
		aLoan = loanRepository.save(aLoan);
		int id = aLoan.getLoanId();

		// Read object from database
		aLoan = loanRepository.FindLoanById(id);

		// Assert that object has correct attributes
		assertNotNull(aLoan);
		assertEquals(id, aLoan.getLoanId());
		assertEquals(art,aLoan.getArtwork());
	}
	
}

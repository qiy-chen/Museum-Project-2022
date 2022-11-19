package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class LoanRepositoryTests {

	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private ArtworkRepository artworkRepository;

	@AfterEach
	public void clearDatabase() {
		artworkRepository.deleteAll();
		loanRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadLoan() {
		
		// Create object
		Loan aLoan = new Loan();

		// Save object
		loanRepository.save(aLoan);
		int loanId = aLoan.getLoanId();

		// Read object from database
		aLoan = null;
		aLoan = loanRepository.findLoanByLoanId(loanId);

		// Assert that object has correct attributes
		assertNotNull(aLoan);
		assertEquals(loanId, aLoan.getLoanId());
	}
	
	@Test
	public void testLoanToArtworkReference() {
		
		// Create object
		Loan aLoan = new Loan();
		loanRepository.save(aLoan); // save before adding art so that it is present for the foreign key when saving the artwork
		int loanID = aLoan.getLoanId();
		
		// create reference
		Artwork art = new Artwork();
		artworkRepository.save(art);
		int artID = art.getArtworkId();
		aLoan.setArtwork(art);

		// Update object
		loanRepository.save(aLoan);

		// Read object from database
		aLoan = null;
		aLoan = loanRepository.findLoanByLoanId(loanID);

		// Assert that object has correct attributes
		assertNotNull(aLoan);
		assertEquals(loanID, aLoan.getLoanId());
		assertEquals(artID,aLoan.getArtwork().getArtworkId());
	}
	
}

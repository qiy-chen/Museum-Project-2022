package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;


@SpringBootTest
public class LoanRepositoryTests {

	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private ArtworkRepository artworkRepository;
	@Autowired
	private MuseumRepository museumRepository;

	@AfterEach
	public void clearDatabase() {
		// order matters : cannot delete entities that are referenced in another table, clear table first
		artworkRepository.deleteAll();
		loanRepository.deleteAll();
		museumRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadLoan() {
		// Create object
		int id = 125;
		Loan aLoan = new Loan();
		aLoan.setLoanId(id);

		// Save object
		loanRepository.save(aLoan);
		int loanId = aLoan.getLoanId();

		// Read object from database
		aLoan = null;
		aLoan = loanRepository.findLoanByLoanId(id);

		// Assert that object has correct attributes
		assertNotNull(aLoan);
		assertEquals(loanId, aLoan.getLoanId());
	}
	
	@Test
	public void testLoanToArtworkReference() {
		// Create object
		int id = 1;
		Loan aLoan = new Loan();
		aLoan.setLoanId(id);
		loanRepository.save(aLoan); // save before adding art so that it is present for the foreign key when saving the artwork
		
		// create references
		Museum aMuseum = new Museum(21);
		museumRepository.save(aMuseum);
		
		int artID = 56;
		Artwork art = new Artwork();
		art.addLoan(aLoan);
		art.setMuseum(aMuseum);
		art.setArtworkId(artID);
		artworkRepository.save(art);
		
		aLoan.setArtwork(art);

		// Update object
		loanRepository.save(aLoan);
		int loanID = aLoan.getLoanId();

		// Read object from database
		aLoan = loanRepository.findLoanByLoanId(loanID);

		// Assert that object has correct attributes
		assertNotNull(aLoan);
		assertEquals(loanID, aLoan.getLoanId());
		assertEquals(artID,aLoan.getArtwork().getArtworkId());
	}
	
}

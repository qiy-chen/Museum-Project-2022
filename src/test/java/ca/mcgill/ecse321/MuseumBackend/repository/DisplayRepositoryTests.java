package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Room;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;

@SpringBootTest
public class DisplayRepositoryTests {
	@Autowired
	private DisplayRepository displayRepository;
	@Autowired
	private ArtworkRepository artworkRepository;

	@AfterEach
	public void clearDatabase() {
		displayRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadDisplay() {
		
		// create object
		Display emmaRoom = new Display();
		emmaRoom = displayRepository.save(emmaRoom);
		int roomId = emmaRoom.getRoomId();
		
		// retrieve from database
		emmaRoom = null;
		emmaRoom = displayRepository.findDisplayByRoomId(roomId);
		
		// check values
		assertNotNull(emmaRoom);
		assertEquals(roomId, emmaRoom.getRoomId());
		
	}
	
	@Test
	public void testRoomToArtworkReference() {
		
		// Create object
		Display aRoom = new Display();
		displayRepository.save(aRoom); // save before adding art so that it is present for the foreign key when saving the artwork
		int roomID = aRoom.getRoomId();
		
		// create reference
		Artwork art = new Artwork();
		artworkRepository.save(art);
		int artID = art.getArtworkId();
		aRoom.addArtwork(art);

		// Update object
		displayRepository.save(aRoom);

		// Read object from database
		aRoom = null;
		aRoom = displayRepository.findDisplayByRoomId(roomID);

		// Assert that object has correct attributes
		assertNotNull(aRoom);
		assertEquals(roomID, aRoom.getRoomId());
		assertEquals(artID,aRoom.getArtwork(0).getArtworkId());
	}
}

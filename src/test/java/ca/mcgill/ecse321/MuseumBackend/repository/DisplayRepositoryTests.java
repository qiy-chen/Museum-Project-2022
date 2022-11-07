package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Room;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;

@SpringBootTest
public class DisplayRepositoryTests {
	@Autowired
	private DisplayRepository displayRepository;
	@Autowired
	private MuseumRepository museumRepository;

	@AfterEach
	public void clearDatabase() {
		displayRepository.deleteAll();
		museumRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadDisplay() {
		int roomNumber = 5;
		int maxArtworks = 300;
		int roomId = 456;
		int museumId = 789;
		Museum museum = new Museum(museumId);
		museum = museumRepository.save(museum);
		Display emmaRoom = new Display();
		emmaRoom.setRoomNumber(roomNumber);
		emmaRoom.setMaxArtworks(maxArtworks);
		emmaRoom.setRoomId(roomId);
		emmaRoom.setMuseum(museum);
		emmaRoom = displayRepository.save(emmaRoom);
		roomId = emmaRoom.getRoomId();
		emmaRoom = null;
		emmaRoom = displayRepository.findDisplayByRoomId(roomId);
		
		assertNotNull(emmaRoom);
		assertEquals(roomId, emmaRoom.getRoomId());
		assertEquals(roomNumber, emmaRoom.getRoomNumber());
		assertEquals(maxArtworks, emmaRoom.getMaxArtworks());
		assertEquals(museum.getMuseumId(), emmaRoom.getMuseum().getMuseumId());
		
	}
}

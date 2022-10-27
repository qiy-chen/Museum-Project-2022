package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.*;


import java.util.UUID;

import org.junit.jupiter.api.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Room;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;

@SpringBootTest
public class DisplayRepositoryTests {
	@Autowired
	private DisplayRepository DisplayRepository;

	@AfterEach
	public void clearDatabase() {
		DisplayRepository.deleteAll();
		Museum.deleteAll();
	}

	@Test
	public void testPersistAndLoadDisplay() {
		int roomNumber = 5;
		int maxArtworks = 300;
		int roomId = 456;
		int museumId = 789;
		Museum museum = new Museum(museumId);
		Room emmaRoom = new Display();
		emmaRoom.setRoomNumber(roomNumber);
		emmaRoom.setMaxArtworks(maxArtworks);
		emmaRoom.setRoomId(roomId);
		emmaRoom.setMuseum(museum);
		displayRepository.save(emmaRoom);
		emmaRoom = null;
		emmaRoom = Repository.findDisplayByARoomID(roomId);
		
		assertNotNull(emmaRoom);
		assertEquals(roomId, emmaRoom.getRoomId());
		assertEquals(roomNumber, emmaRoom.getRoomNumber());
		assertEquals(maxArtworks, emmaRoom.getMaxArtworks());
		assertEquals(museum, emmaRoom.getMuseum());
		
	}
}

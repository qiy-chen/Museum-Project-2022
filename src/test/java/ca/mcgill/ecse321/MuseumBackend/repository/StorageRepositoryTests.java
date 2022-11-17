package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;


@SpringBootTest
public class StorageRepositoryTests {

    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private ArtworkRepository artworkRepository;

    @AfterEach
    public void clearDatabase() {
      storageRepository.deleteAll();
      artworkRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadStorage() {
        
    	// setup object
    	int roomNumber = 123;
        Storage storage = new Storage();
        storage.setRoomNumber(roomNumber);
        
        // save to database
        storage = storageRepository.save(storage);
        int roomId = storage.getRoomId();
        storage = null;

        // retrieve from database
        storage = storageRepository.findStorageByRoomId(roomId);
        
        // check results
        assertNotNull(storage);
        assertEquals(storage.getRoomId(), roomId);
        assertEquals(storage.getRoomNumber(), roomNumber);
    }
    
    @Test
	public void testRoomToArtworkReference() {
		
		// Create object
		Storage aRoom = new Storage();
		storageRepository.save(aRoom); // save before adding art so that it is present for the foreign key when saving the artwork
		int roomID = aRoom.getRoomId();
		
		// create reference
		Artwork art = new Artwork();
		artworkRepository.save(art);
		int artID = art.getArtworkId();
		aRoom.addArtwork(art);

		// Update object
		storageRepository.save(aRoom);

		// Read object from database
		aRoom = null;
		aRoom = storageRepository.findStorageByRoomId(roomID);

		// Assert that object has correct attributes
		assertNotNull(aRoom);
		assertEquals(roomID, aRoom.getRoomId());
		assertEquals(artID,aRoom.getArtwork(0).getArtworkId());
	}
}
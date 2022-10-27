package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;


<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Room;


@SpringBootTest
public class StorageRepositoryTests {
    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private StorageRepository storageRepository;

    @AfterEach
    public void clearDatabase() {
      museumRepository.deleteAll();
      storageRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadMuseum() {
        int roomNumber = 25;
<<<<<<< Updated upstream
        
        Room storage = new Storage();
        
        storage.setRoomNumber(roomNumber);
        int storageID = storage.getRoomId();
        
        storageRepository.save(storage);
        
        storage = null;
        
        storage = storageRepository.findStorageByStorageId(storageID);
        assertNotNull(storage);
        assertEquals(storage.getRoomId(), storageID);
        assertEquals(storage.getRoomNumber(), roomNumber);
        
       
=======
        int roomId = 123;
        int museumID = 456;
        
        Museum museum = new Museum(museumID);
        
        Room storage = new Storage(roomNumber, roomId, museum);
        
        int storageID = storage.getRoomId();
        int storageNumber = storage.getRoomNumber();
        
        storageRepository.save(storage);
        museumRepository.save(museum);
        
        storage = null;
        museum = null;
        
        storageDB = storageRepository.findStorageByStorageId(storageID);
        museumDB = museumRepository.findMuseumByMuseumId(museumID);
        
        assertNotNull(storageDB);
        assertEquals(storageDB.getRoomId(), storageID);
        assertEquals(storageDB.getRoomNumber(), storageNumber);
        assertEquals(storageDB.getMuseum(), museumDB);
>>>>>>> Stashed changes
    }
}

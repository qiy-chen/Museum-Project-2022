package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.model.Room;


@SpringBootTest
public class StorageRepositoryTests {
    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private StorageRepository storageRepository;

    @AfterEach
    public void clearDatabase() {
      storageRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadMuseum() {
        int roomNumber = 25;
        int roomId = 123;
        
        Room storage = new Storage(roomNumber, roomId);
        
        storageRepository.save(storage);
        
        storage = null;
        
        storage = storageRepository.findStorageByStorageId(storageID);
        
        assertNotNull(storage);
        assertEquals(storage.getRoomId(), roomId);
        assertEquals(storage.getRoomNumber(), roomNumber);

    }
}

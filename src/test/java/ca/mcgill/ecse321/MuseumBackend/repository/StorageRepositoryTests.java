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
import ca.mcgill.ecse321.MuseumBackend.model.Museum;


@SpringBootTest
public class StorageRepositoryTests {

    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private MuseumRepository museumRepository;

    @AfterEach
    public void clearDatabase() {
      storageRepository.deleteAll();
      museumRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadMuseum() {
        int roomNumber = 25;
        int roomId = 123;
        int museumId = 456;
        
        Museum museum = new Museum(museumId);
        museum = museumRepository.save(museum);
        Storage storage = new Storage(roomNumber, roomId, museum);
        

        
        storage = storageRepository.save(storage);
        roomId = storage.getRoomId();
        storage = null;

        storage = storageRepository.findStorageByRoomId(roomId);
        
        assertNotNull(storage);
        assertEquals(storage.getRoomId(), roomId);
        assertEquals(storage.getRoomNumber(), roomNumber);
        assertEquals(storage.getMuseum().getMuseumId(), museum.getMuseumId());
    }
}
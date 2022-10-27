package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;


@SpringBootTest
public class MuseumRepositoryTests {
    @Autowired
    private MuseumRepository museumRepository;

    @AfterEach
    public void clearDatabase() {
      museumRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadMuseum() {
      //Creating the museum instance 
      Museum museum = new Museum()
      
      //Save the museum instance in the MuseumRepository table 
      museumRepository.save(museum);
      
      //Obtain museumID of created museum object that was saved in MuseumRepository table 
      int museumID = museum.getMuseumId();
      
      Museum museumDB = MuseumRepository.findMuseumByMuseumId(museumID);
      
      //Assertion Tests
      assertNotNull(museumDB);
      assertEquals(museumID, museumDB.getMuseumID());
    }
}

package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

      int id = 123;
      Museum museum = new Museum(id);
      museum = museumRepository.save(museum);

      int ticketId = 456;
      
      museumRepository.save(museum);
      
      int museumID = museum.getMuseumId();
 
      museum = null;
      
      museum = museumRepository.findMuseumByMuseumId(museumID);
            
      //Assertion Tests
      assertNotNull(museum);
      assertEquals(museumID, museum.getMuseumId());
      assertEquals(ticketId, museum.getTicket(0).getTicketId());
    }
}

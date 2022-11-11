package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;


@SpringBootTest
public class MuseumRepositoryTests {
    @Autowired
    private MuseumRepository museumRepository;
    
    @Autowired
    private TicketRepository ticketRepository;

    @AfterEach
    public void clearDatabase() {
      ticketRepository.deleteAll();
      museumRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadMuseum() {
      //Creating the museum instance 

      int id = 123;
      Museum museum = new Museum(id);
      museum = museumRepository.save(museum);

      int ticketId = 456;
      
      Ticket ticket = new Ticket();
      ticket.setTicketId(ticketId);
      ticket.setMuseum(museum);
      
      //Save the museum instance in the MuseumRepository table 
      museum = museumRepository.save(museum);
      
      //Obtain museumID of created museum object that was saved in MuseumRepository table 
      int museumID = museum.getMuseumId();
      
      museum = null;
      
      museum = museumRepository.findMuseumByMuseumId(museumID);
            
      //Assertion Tests
      assertNotNull(museum);
      assertEquals(museumID, museum.getMuseumId());
      assertEquals(ticketId, museum.getTicket(0).getTicketId());
    }
}

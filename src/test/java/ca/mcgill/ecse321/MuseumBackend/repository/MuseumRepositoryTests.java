package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
      museumRepository.deleteAll();
      ticketRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadMuseum() {
      
    	//Creating the museum instance 
      Museum museum = new Museum();
      museum = museumRepository.save(museum);
      
      // create and add a ticket
      Ticket ticket = new Ticket();
      Boolean added = museum.addTicket(ticket);
      System.out.println(added);
      
      // save the changes
      ticket = ticketRepository.save(ticket);
      int ticketId = ticket.getTicketId();
      museum = museumRepository.save(museum);
      int museumID = museum.getMuseumId();
      
      // Retrieve the museum from the database
      museum = null;
      museum = museumRepository.findMuseumByMuseumId(museumID);

      // Check results
      assertNotNull(museum);
      assertEquals(museumID, museum.getMuseumId());
      assertTrue(museum.getTickets().size() > 0);
      assertEquals(ticketId, museum.getTicket(0).getTicketId());
    }
}

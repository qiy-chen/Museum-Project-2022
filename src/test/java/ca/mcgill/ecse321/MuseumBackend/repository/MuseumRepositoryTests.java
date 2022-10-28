package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;

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
    }
    
    @Test
    public void testPersistAndLoadMuseum() {
      //Creating the museum instance 

      int id = 123;
      Museum museum = new Museum(id);
      
      //double price = 19.99;
      //int ticketId = 456;
      //Date date = new Date(2022, 04, 18);
      
      //Ticket ticket = new Ticket (price, ticketId, date);
      //ticket.setMuseum(museum);
      
      //Save the museum instance in the MuseumRepository table 
      museumRepository.save(museum);
      //ticketRepository.save(ticket);
      
      //Obtain museumID of created museum object that was saved in MuseumRepository table 
      int museumID = museum.getMuseumId();
      //double ticketPrice = ticket.getPrice();
      //int ticketID = ticket.getTicketId();
      //Date ticketID = ticket.getTicketDate();
      
      //int ticketIndex = museum.getTicketIndex(ticket);
      museum = null;
      
      museum = museumRepository.findMuseumByMuseumId(museumID);
            
      //Assertion Tests
      assertNotNull(museum);
      assertEquals(museumID, museum.getMuseumId());
      //assertEquals(ticket, museum.getTicket(ticketIndex));
    }
}

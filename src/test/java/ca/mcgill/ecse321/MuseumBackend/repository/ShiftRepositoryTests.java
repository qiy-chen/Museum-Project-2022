package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

@SpringBootTest
public class ShiftRepositoryTests {
  @Autowired
  private ShiftRepository shiftRepository;
  @Autowired
  private MuseumRepository museumRepository;
  

  @AfterEach
  public void clearDatabase() {
    shiftRepository.deleteAll();
    museumRepository.deleteAll();
  }
  @Test
  public void testPersistAndLoadTicket() {
    //Setup museum instance
    int museumId = 0;
    Museum museumInstance = new Museum(museumId);

    //Setup shift
    Date startDate = new Date(2);
    Date endDate = new Date(3);
    int shiftId = 33;
    Shift shift = new Shift(startDate, endDate, shiftId, museumInstance);
    
    museumRepository.save(museumInstance);
    shift = shiftRepository.save(shift);
    
    //Set all values of objects to null
    shift = null;
    museumInstance = null;
    
    //Search for the ticket in the database
    shift = shiftRepository.findShiftByShiftId(shiftId);
    
    //Test if the values are correct
    assertNotNull(shift);
    assertEquals(shiftId, shift.getWorkDayId());
    assertEquals(startDate, shift.getStartTime());
    assertEquals(endDate, shift.getEndTime());
    
    assertNotNull(shift.getMuseum());
    assertEquals(museumId, shift.getMuseum().getMuseumId());
    
  }
  
}

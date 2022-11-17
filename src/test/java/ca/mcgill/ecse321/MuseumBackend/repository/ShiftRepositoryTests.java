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
    int museumId = 10;
    Museum museumInstance = new Museum(museumId);

    museumInstance = museumRepository.save(museumInstance);
    museumId = museumInstance.getMuseumId();
    //Setup shift
    Date startDate = new Date(0);
    Date endDate = new Date(2);


    Shift shift = new Shift();
    shift.setMuseum(museumInstance);
    shift.setStartTime(startDate);
    shift.setEndTime(endDate);


    shift = shiftRepository.save(shift);
    int shiftId = shift.getWorkDayId();
    startDate = shift.getStartTime();
    endDate = shift.getEndTime();
    //Set all values of objects to null
    shift = null;
    museumInstance = null;

    //Search for the ticket in the database
    shift = shiftRepository.findShiftByWorkDayId(shiftId);

    //Test if the values are correct
    assertNotNull(shift);
    assertEquals(shiftId, shift.getWorkDayId());
    assertEquals(startDate.toString(), shift.getStartTime().toString());
    assertEquals(endDate.toString(), shift.getEndTime().toString());

    assertNotNull(shift.getMuseum());
    assertEquals(museumId, shift.getMuseum().getMuseumId());

  }

}
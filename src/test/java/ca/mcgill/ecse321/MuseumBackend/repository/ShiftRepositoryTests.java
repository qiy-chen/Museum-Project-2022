package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

@SpringBootTest
public class ShiftRepositoryTests {
  @Autowired
  private ShiftRepository shiftRepository;
  @Autowired
  private EmployeeRepository employeeRepository;

  @AfterEach
  public void clearDatabase() {
    shiftRepository.deleteAll();
    employeeRepository.deleteAll();
  }
  @Test
  public void testPersistAndLoadTicket() {

    //Setup shift
    Date startDate = new Date(0);
    Date endDate = new Date(2);
    Shift shift = new Shift();
    shift.setStartTime(startDate);
    shift.setEndTime(endDate);
    shift.setWorkDayId(12);
    // save shift
    shift = shiftRepository.save(shift);
    int shiftId = shift.getWorkDayId();
    
    // setup reference
    Employee aragon = new Employee();
    employeeRepository.save(aragon);
    int employeeId = aragon.getPersonRoleId();
    
    shift.addEmployee(aragon);
    shiftRepository.save(shift);
    
    //Search for the ticket in the database 
    shift = null;
    shift = shiftRepository.findShiftByWorkDayId(shiftId);
    
    //Test if the attributes are correct
    assertNotNull(shift);
    assertEquals(shiftId, shift.getWorkDayId());
    assertEquals(startDate.toString(), shift.getStartTime().toString());
    assertEquals(endDate.toString(), shift.getEndTime().toString());
    
    // check if the reference is correct
    assertTrue(shift.getEmployees().size() > 0);
    assertEquals(employeeId, shift.getEmployee(0).getPersonRoleId());
  }

}
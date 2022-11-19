package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShiftRepositoryTests {
  @Autowired
  private ShiftRepository shiftRepository;
  @Autowired
  private EmployeeRepository employeeRepository;

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  @AfterEach
  public void clearDatabase() {
    shiftRepository.deleteAll();
    employeeRepository.deleteAll();
  }
  @Test
  public void testPersistAndLoadTicket() {

    //Setup shift
    LocalDateTime startDate = LocalDateTime.parse("2022-11-15 08:00",formatter);
    LocalDateTime endDate = LocalDateTime.parse("2022-11-15 17:00",formatter);
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
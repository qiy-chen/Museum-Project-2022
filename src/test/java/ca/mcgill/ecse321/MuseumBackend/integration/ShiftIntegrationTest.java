package ca.mcgill.ecse321.MuseumBackend.integration;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ShiftRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ShiftResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.ShiftRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShiftIntegrationTest {

    private int workDayId = 54;
    private double sixHoursInMillisecond = Double.parseDouble("2.16e+7");
    private long workHours = (long) sixHoursInMillisecond;
    private Date startTime = new Date(Calendar.getInstance().getTimeInMillis());
    private Date endTime = new Date(Calendar.getInstance().getTimeInMillis() + workHours);


    @Autowired
    private TestRestTemplate client;

    @Autowired
    private MuseumRepository museumRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        shiftRepository.deleteAll();
        employeeRepository.deleteAll();
        personRepository.deleteAll();
        museumRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetShift() {
        int workDayId = testCreateShift();
        testGetShift(workDayId);
    }

    private int testCreateShift() {
        Museum museum = new Museum(12);
        museum = museumRepository.save(museum);
        ResponseEntity<ShiftResponseDto> response = client.postForEntity("/shifts", new ShiftRequestDto(this.startTime,this.endTime,this.workDayId,museum), ShiftResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(this.workDayId, response.getBody().getWorkDayId(),"Response has correct id");
        assertTrue(response.getBody().getWorkDayId() > 0, "Response has valid ID");
        return response.getBody().getWorkDayId();
    }

    private void testGetShift(int workDayId) {
        ResponseEntity<ShiftResponseDto> response = client.getForEntity("/shift/" + workDayId, ShiftResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(this.workDayId, response.getBody().getWorkDayId(), "Response has correct id");

    }

    @Test
    public void testCreateGetAndAddEmployee() {
        int workDayId = testCreateShift();
        testGetShift(workDayId);
    }

    private void testAddEmployee(int workDayId, EmployeeRequestDto employeeRequestDto) {
        ResponseEntity<EmployeeResponseDto> employeeResponse = client.postForEntity("/employee", employeeRequestDto, EmployeeResponseDto.class);
    }

    /*@Test
    public void testCreateGetAndDeleteShift() {
        int workDayId = testCreateShift();
        testGetShift(workDayId);
        testDeleteShift(workDayId);

    }

    private void testDeleteShift(int workDayId) {
        client.delete("/shift/"+workDayId);
        try {
            client.getForEntity("/shift/"+workDayId,ShiftResponseDto.class);
            fail("Shift was found");
        } catch(MuseumBackendException e) {
            assertEquals(HttpStatus.NOT_FOUND,e.getStatus());
            assertEquals("Shift not found.", e.getMessage());
        }
    }*/
}



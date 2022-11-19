package ca.mcgill.ecse321.MuseumBackend.integration;

import ca.mcgill.ecse321.MuseumBackend.exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.*;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShiftIntegrationTest {

    private int workDayId = 54;
    private double sixHoursInMillisecond = Double.parseDouble("2.16e+7");
    private long workHours = (long) sixHoursInMillisecond;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
    private LocalDateTime startTime = LocalDateTime.parse("2022-11-18 8:00",formatter);
    private LocalDateTime endTime = LocalDateTime.parse("2022-11-18 17:00",formatter);


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
        ResponseEntity<ShiftResponseDto> response = client.postForEntity("/shift", new ShiftRequestDto(this.startTime,this.endTime,this.workDayId,museum), ShiftResponseDto.class);
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
    public void testCreateAndGetShiftAndChangeShiftDate() {
        int workDayId = testCreateShift();
        testGetShift(workDayId);
        testChangeDateOfShift(workDayId, "2022-11-17 8:00", "2022-11-17 17:00");
    }

    @Test
    public void testCreateAndGetShiftAndAddEmployeeToShift() {
        int workDayId = testCreateShift();
        testGetShift(workDayId);
        Person sam = new Person();
        sam.setEmail("sfaubert9@gmail.com");
        personRepository.save(sam);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setEmail("sfaubert9@gmail.com");
        testAddEmployeeToShift(workDayId,employeeRequestDto);
    }

    private void testAddEmployeeToShift(int workDayId, EmployeeRequestDto employeeRequestDto) {
        ResponseEntity<EmployeeResponseDto> employeeResponse = client.postForEntity("/employee", employeeRequestDto, EmployeeResponseDto.class);
        Map<String, Integer> idMap = new HashMap<>();
        idMap.put("workDayId", workDayId);
        idMap.put("employeeId", employeeResponse.getBody().getId());
        ResponseEntity<ShiftResponseDto> shiftResponseDtoResponseEntity = client.postForEntity("/shift/employees", idMap, ShiftResponseDto.class);
        assertEquals(HttpStatus.OK, shiftResponseDtoResponseEntity.getStatusCode());
        assertEquals(employeeResponse.getBody().getId(), shiftResponseDtoResponseEntity.getBody().getEmployees().get(0));
    }

    private void testChangeDateOfShift(int workDayId, String startTimeValue, String endTimeValue) {
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("startTimeValue", startTimeValue);
        dateMap.put("endTimeValue", endTimeValue);
        ResponseEntity<ShiftResponseDto> response0 = client.getForEntity("/shift/"+workDayId, ShiftResponseDto.class);
        client.put("/shift/"+workDayId+"/", dateMap);
        ResponseEntity<ShiftResponseDto> response = client.getForEntity("/shift/"+workDayId, ShiftResponseDto.class);
        assertEquals(LocalDateTime.parse(startTimeValue,formatter), response.getBody().getStartTime());
        assertEquals(LocalDateTime.parse(endTimeValue,formatter), response.getBody().getEndTime());
        assertNotEquals(response0.getBody().getStartTime(), response.getBody().getStartTime());
        assertNotEquals(response0.getBody().getEndTime(),response.getBody().getEndTime());
    }


    @Test
    public void testCreateAndGetShiftAndRemoveEmployeeFromShift() {
        int workDayId = testCreateShift();
        testGetShift(workDayId);
        Person sam = new Person();
        sam.setEmail("sfaubert9@gmail.com");
        personRepository.save(sam);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setEmail("sfaubert9@gmail.com");
        testRemoveEmployeeFromShift(workDayId, employeeRequestDto);

    }

    private void testRemoveEmployeeFromShift(int workDayId, EmployeeRequestDto employeeRequestDto) {
        ResponseEntity<EmployeeResponseDto> employeeResponse = client.postForEntity("/employee", employeeRequestDto, EmployeeResponseDto.class);
        Map<String, Integer> idMap = new HashMap<>();
        idMap.put("workDayId", workDayId);
        idMap.put("employeeId", employeeResponse.getBody().getId());
        ResponseEntity<ShiftResponseDto> shiftResponse0 = client.postForEntity("/shift/employees", idMap, ShiftResponseDto.class);
        int employeeId = shiftResponse0.getBody().getEmployees().get(0);
        client.put("/shift/employees", idMap);
        ResponseEntity<ShiftResponseDto> shiftResponse = client.getForEntity("/shift/"+workDayId, ShiftResponseDto.class);
        assertFalse(shiftResponse.getBody().getEmployees().contains(employeeId));

    }

    @Test
    public void testCreateGetAndDeleteShift() {
        int workDayId = testCreateShift();
        testGetShift(workDayId);
        testDeleteShift(workDayId);

    }

    private void testDeleteShift(int workDayId) {
        client.put("/shift/"+workDayId,null);
        try {
            client.getForEntity("/shift/"+workDayId,ShiftResponseDto.class);
            fail("Shift was found");
        } catch(RestClientException | IllegalArgumentException e) {

        }
    }
}



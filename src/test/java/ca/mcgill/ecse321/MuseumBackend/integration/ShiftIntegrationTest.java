package ca.mcgill.ecse321.MuseumBackend.integration;

import ca.mcgill.ecse321.MuseumBackend.dto.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShiftIntegrationTest {

    /**
     * @author Samuel Faubert
     */
    private int workDayId = 1;
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
    private int museumId;

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
        Museum museum = new Museum();
        museum = museumRepository.save(museum);
        this.museumId = museum.getMuseumId();
        ResponseEntity<ShiftResponseDto> response = client.postForEntity("/shift", new ShiftRequestDto(this.startTime,this.endTime,this.workDayId,museum), ShiftResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertTrue(response.getBody().getWorkDayId() > 0, "Response has valid ID");
        return response.getBody().getWorkDayId();
    }

    private void testGetShift(int workDayId) {
        ResponseEntity<ShiftResponseDto> response = client.getForEntity("/shift/" + workDayId, ShiftResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(workDayId, response.getBody().getWorkDayId(), "Response has correct id");

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
        PersonRequestDto personRequestDto = new PersonRequestDto("sfaubert9@gmail.com","yoyo","Sam",museumRepository.findMuseumByMuseumId(this.museumId));
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setEmail("sfaubert9@gmail.com");
        testAddEmployeeToShift(workDayId,personRequestDto,employeeRequestDto);
    }

    private int testAddEmployeeToShift(int workDayId, PersonRequestDto personRequestDto, EmployeeRequestDto employeeRequestDto) {
        ResponseEntity<PersonResponseDto> personResponse = client.postForEntity("/person", personRequestDto, PersonResponseDto.class);
        ResponseEntity<EmployeeResponseDto> employeeResponse = client.postForEntity("/employee", employeeRequestDto, EmployeeResponseDto.class);
        ResponseEntity<ShiftResponseDto> shiftResponseDtoResponseEntity = client.postForEntity("/shift/employees/"+workDayId, employeeResponse.getBody().getId(), ShiftResponseDto.class);
        assertEquals(HttpStatus.OK, shiftResponseDtoResponseEntity.getStatusCode());
        int index = shiftResponseDtoResponseEntity.getBody().getEmployees().indexOf(employeeResponse.getBody().getId());
        assertEquals(employeeResponse.getBody().getId(), shiftResponseDtoResponseEntity.getBody().getEmployees().get(index));
        return employeeResponse.getBody().getId();
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
        PersonRequestDto personRequestDto = new PersonRequestDto("sfaubert9@gmail.com","yoyo","Sam",museumRepository.findMuseumByMuseumId(this.museumId));
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setEmail("sfaubert9@gmail.com");
        int employeeId = testAddEmployeeToShift(workDayId,personRequestDto,employeeRequestDto);
        testRemoveEmployeeFromShift(workDayId, employeeId);

    }

    private void testRemoveEmployeeFromShift(int workDayId, int employeeId) {
        ResponseEntity<ShiftResponseDto> shiftResponse0 = client.getForEntity("/shift/"+workDayId, ShiftResponseDto.class);
        ResponseEntity<EmployeeResponseDto> employeeResponse = client.getForEntity("/employee/"+employeeId,EmployeeResponseDto.class);
        client.put("/shift/employees/"+workDayId, employeeResponse.getBody().getId());
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
        } catch(RestClientException | IllegalArgumentException ignored) {

        }
    }

    @Test
    public void testCreateAndGetShiftAndCreateAndGetEmployees() {
       List<Integer> employeeIds = new ArrayList<>();
        int workDayId = testCreateShift();
        testGetShift(workDayId);
        PersonRequestDto personRequestDto0 = new PersonRequestDto("sfaubert9@gmail.com","yoyo","Sam",museumRepository.findMuseumByMuseumId(this.museumId));
        EmployeeRequestDto employeeRequestDto0 = new EmployeeRequestDto();
        employeeRequestDto0.setEmail("sfaubert9@gmail.com");
        employeeIds.add(testAddEmployeeToShift(workDayId,personRequestDto0,employeeRequestDto0));
        PersonRequestDto personRequestDto1 = new PersonRequestDto("alex@gmail.com","yoyoA","Alex",museumRepository.findMuseumByMuseumId(this.museumId));
        EmployeeRequestDto employeeRequestDto1 = new EmployeeRequestDto();
        employeeRequestDto1.setEmail("alex@gmail.com");
        employeeIds.add(testAddEmployeeToShift(workDayId,personRequestDto1,employeeRequestDto1));
        PersonRequestDto personRequestDto2 = new PersonRequestDto("emma@gmail.com","yoyoE","Emma",museumRepository.findMuseumByMuseumId(this.museumId));
        EmployeeRequestDto employeeRequestDto2 = new EmployeeRequestDto();
        employeeRequestDto2.setEmail("emma@gmail.com");
        employeeIds.add(testAddEmployeeToShift(workDayId,personRequestDto2,employeeRequestDto2));
        testGetShiftEmployees(workDayId, employeeIds);

    }
    private void testGetShiftEmployees(int workDayId, List<Integer> employeeIds) {
        ResponseEntity<Integer[]> employeeListResponse = client.getForEntity("/shift/employees/"+workDayId, Integer[].class);
        List<Integer> responseEmployeeIds = Arrays.stream(employeeListResponse.getBody()).toList();
        for(Integer e : employeeIds) {
            assertTrue(responseEmployeeIds.contains(e));
        }
    }
    @Test
    public void testCreateShiftsAndGetShifts() {
        int[] workDayIds = new int[3];
        workDayIds[0] = testCreateShift();
        this.startTime = LocalDateTime.parse("2022-11-19 8:00",this.formatter);
        this.endTime = LocalDateTime.parse("2022-11-19 17:00",this.formatter);
        workDayIds[1] = testCreateShift();
        this.startTime = LocalDateTime.parse("2022-11-20 8:00",this.formatter);
        this.endTime = LocalDateTime.parse("2022-11-20 17:00",this.formatter);
        workDayIds[2] = testCreateShift();
        testGetShifts(workDayIds);


    }
    private void testGetShifts(int[] workDayIds) {
        ResponseEntity<ShiftResponseDto[]> shiftsResponse = client.getForEntity("/shift",ShiftResponseDto[].class);
        int i =0;
        for(int workDayId : workDayIds) {
            assertEquals(workDayId,shiftsResponse.getBody()[i].getWorkDayId());
            i++;
        }
    }


}



package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.ShiftRepository;
/**
 * @Author Jeanine Looman
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // set random port
public class TestEmployeeIntegration {

	@Autowired
	private TestRestTemplate client;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private PersonRepository personRepo;
	@Autowired
	private ShiftRepository shiftRepo;

	@BeforeEach
	public void wipeDatabase() {
		personRepo.deleteAll();
		employeeRepo.deleteAll();
		shiftRepo.deleteAll();
	}

	@AfterEach
	public void clearDatabase() {
		personRepo.deleteAll();
		employeeRepo.deleteAll();
		shiftRepo.deleteAll();
	}

	@Test
	public void testCreateAndGetEmployee() {
		int id = testCreateEmployee("obi@kenobi.com");
		testGetEmployee(id, "obi@kenobi.com");
	}

	private int testCreateEmployee(String email) {

		// setup - first create and save person that will get the role employee
		Person person = new Person();
		person.setEmail(email);
		personRepo.save(person);

		// call method: create a new employee
		ResponseEntity<EmployeeDto> response = client.postForEntity("/employee", new EmployeeDto(email),
				EmployeeDto.class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals(email, response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id > 0, "Response has valid ID");

		return response.getBody().id;
	}

	private void testGetEmployee(int id, String email) {

		// call method: get the employee by their id
		ResponseEntity<EmployeeDto> response = client.getForEntity("/employee/" + id, EmployeeDto.class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals(email, response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id == id, "Response has correct ID");
	}

	@Test
	public void testCreateInvalidEmployee() {
		ResponseEntity<String> response = client.postForEntity("/employee", new EmployeeDto("   "), String.class);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
	}

	@Test
	public void testGetInvalidEmployee() {
		getInvalidEmployee(Integer.MAX_VALUE);
	}

	public void getInvalidEmployee(int invalidID) {

		ResponseEntity<String> response = client.getForEntity("/employee/" + invalidID, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
		assertEquals("Employee not found.", response.getBody(), "Response has correct error message");
	}

	// test get all employees
	@Test
	public void testGetAllEmployees() {

		// setup - make two employees
		int bilboID = testCreateEmployee("bilbo@baggins.com");
		int gandalfID = testCreateEmployee("gandalf@grey.com");

		// call method: get the employee by their id
		ResponseEntity<EmployeeDto[]> response = client.getForEntity("/employees", EmployeeDto[].class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		EmployeeDto[] employees = response.getBody();
		assertEquals(2, employees.length, "Response has all employees");
		assertEquals(bilboID, employees[0].id, "Correct ID");
		assertEquals(gandalfID, employees[1].id, "Correct ID");
	}

	// test fire employee
	@Test
	public void testFireEmployee() {

		// setup - make employee and check they are saved
		String email = "bilbo@baggins.com";
		int bilboID = testCreateEmployee(email);
		testGetEmployee(bilboID, email);

		// fire the employee
		testFireEmployee(bilboID);

		// check that they cannot be found
		getInvalidEmployee(bilboID);
	}

	private void testFireEmployee(int id) {
		client.delete("/employee/" + id);
		try {
			client.getForEntity("/employee/" + id, EmployeeResponseDto.class);
			fail("Person was found!");
		} catch (RestClientException | IllegalArgumentException e) {
		}
	}

	// test get all shifts for employee
	/*@Test
	public void testGetShiftsForEmployee() {

		String email = "hey@bud.com";
		int employeeID = testCreateEmployee(email);

		// give the employee shifts
		Employee employee = employeeRepo.findEmployeeByPersonRoleId(employeeID);
		Shift nightShift = new Shift();
		Shift dayShift = new Shift();
		shiftRepo.save(nightShift);
		shiftRepo.save(dayShift);
		int nightID = nightShift.getWorkDayId();
		int dayID = dayShift.getWorkDayId();
		employee.addShift(nightShift);
		employee.addShift(dayShift);
		employeeRepo.save(employee);

		// set up what we expect to get
		List<ShiftDto> shiftDtos = new ArrayList<>();
		shiftDtos.add(new ShiftDto(nightID));
		shiftDtos.add(new ShiftDto(dayID));

		// call method: get the employee by their id
		ResponseEntity<ShiftDto[]> response = client.getForEntity("/employee/shifts/" + employeeID, ShiftDto[].class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		ShiftDto[] shifts = response.getBody();
		assertEquals(2, shifts.length, "Response has all shifts");
		assertEquals(nightID, shifts[1].workDayId, "Correct ID");
		assertEquals(dayID, shifts[0].workDayId, "Correct ID");
	}
*/
}

class EmployeeDto {
	public int id;
	public String email;

	// Need default constructor so that Jackson can instantiate the object
	public EmployeeDto() {
	}

	public EmployeeDto(String email) {
		this.email = email;
	}
	
	public EmployeeDto(int id) {
		this.id = id;
	}
}


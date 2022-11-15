package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // set random port
public class TestEmployeeIntegration {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private PersonRepository personRepo;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		employeeRepo.deleteAll();
		personRepo.deleteAll();
	}

	@Test
	public void testCreateAndGetEmployee() {
		int id = testCreateEmployee();
		testGetEmployee(id);
	}

	private int testCreateEmployee() {

		// setup - first create and save person that will get the role employee
		Person person = new Person();
		String email = "obi@kenobi.com";
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

	private void testGetEmployee(int id) {

		// call method: get the employee by their id
		ResponseEntity<EmployeeDto> response = client.getForEntity("/employee/" + id, EmployeeDto.class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("obi@kenobi.com", response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id == id, "Response has correct ID");
	}

	@Test
	public void testCreateInvalidEmployee() {
		ResponseEntity<String> response = client.postForEntity("/employee", new EmployeeDto("   "), String.class);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	}

	@Test
	public void testGetInvalidEmployee() {
		ResponseEntity<String> response = client.getForEntity("/employee/" + Integer.MAX_VALUE, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
		assertEquals("Employee not found.", response.getBody(), "Response has correct error message");
	}

	// test get all employees
	@Test
	public void testGetAllEmployees() {
		// setup: make list of employees to find
		Employee baggins = new Employee();
		Employee smeagol = new Employee();
		int bagginsID = baggins.getPersonRoleId();
		int smeagolsID = smeagol.getPersonRoleId();

		ArrayList<Employee> hobbits = new ArrayList<>();
		hobbits.add(baggins);
		hobbits.add(smeagol);

		// call method: get the employee by their id
		ResponseEntity<EmployeeDto[]> response = client.getForEntity("/employees", EmployeeDto[].class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		EmployeeDto[] employees = response.getBody();
		assertTrue((employees.length == 2), "Response has all employees");
		assertEquals(bagginsID, employees[0].id, "Response has correct first ID");
		assertEquals(smeagolsID, employees[1].id, "Response has correct second ID");
	}
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
}

package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
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
	public void wipeDatabase() {
		personRepo.deleteAll();
		employeeRepo.deleteAll();
	}
	
	@AfterEach
	public void clearDatabase() {
		personRepo.deleteAll();
		employeeRepo.deleteAll();
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
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
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

		// setup -  make two employees
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
//	@Test
//	public void testFireEmployee() {
//		
//		// setup - make employee and check they are saved
//		String email = "bilbo@baggins.com";
//		int bilboID = testCreateEmployee(email);
//		testGetEmployee(bilboID, email);
//		
//		// fire the employee
//		ResponseEntity<EmployeeDto> response = client.exchange("/employee/fire/" + bilboID, HttpMethod.DELETE, null, EmployeeDto.class);
//		
//		System.out.println(response.getStatusCodeValue());
//		
//		//check that they cannot be found
//		getInvalidEmployee(bilboID);
//	}
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

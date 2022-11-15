package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class TestEmployeeService {

	// Replace the repository with a "mock" that exposes the same interface
	@Mock
	EmployeeRepository employeeRepo;
	@Mock
	PersonRepository personRepo;

	// Get a service that uses the mock repository
	@InjectMocks
	EmployeeService employeeService;
	@InjectMocks
	EmployeeService personService;

	@Test
	public void testGetEmployeeById() {
		// Tell the mocked repository how to behave
		final int id = 1;
		final Employee hannah = new Employee();
		hannah.setPersonRoleId(id);
		when(employeeRepo.findEmployeeByPersonRoleId(id)).thenAnswer(x -> hannah);

		// Test that the service behaves properly
		Employee employee = employeeService.getEmployeeById(id);

		assertNotNull(employee);
		assertEquals(id, employee.getPersonRoleId());
	}

	@Test
	public void testGetEmployeeByInvalidId() {
		final int invalidId = 99;

		// Mock: if asking for a employee with invalid ID, return null
		when(employeeRepo.findEmployeeByPersonRoleId(invalidId)).thenAnswer(x -> null);

		// call method, and obtain resulting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> employeeService.getEmployeeById(invalidId));

		// check results
		assertEquals("Employee not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}

	@Test
	public void testCreateEmployee() {
		// Mock: just return the Employee with no modification
		when(employeeRepo.save(any(Employee.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

		// test set up - create a person
		Person person = new Person();
		String email = "finnigan@mail.com";
		String name = "Finn Igan";
		person.setEmail(email);
		person.setName(name);
		// when searching for person using email, return person
		when(personRepo.findPersonByEmail(email)).thenAnswer(x -> person);
		// create the employee request and tie it to the existing person
		EmployeeRequestDto finn = new EmployeeRequestDto();
		finn.setEmail(email);

		// call method
		EmployeeResponseDto returnedEmployee = employeeService.createEmployee(email);

		// check results
		assertNotNull(returnedEmployee);
		assertEquals(email, returnedEmployee.getPerson().getEmail());
		// Check that the service actually saved the employee
		// verify(employeeRepo, times(1)).save();
	}

	// check if creating employee given person who is already an employee throws exception
	@Test
	public void testInvalidCreateExistingEmployee() {
		
		// test set up - create a person who is already an employee
		Person person = new Person();
		String email = "finnigan@mail.com";
		String name = "Finn Igan";
		person.setEmail(email);
		person.setName(name);
		Employee existingEmployee = new Employee();
		person.addPersonRole(existingEmployee);
		// when searching for person using email, return person
		when(personRepo.findPersonByEmail(email)).thenAnswer(x -> person);
		// create the employee request and tie it to the existing person
		//EmployeeRequestDto invalidRequest = new EmployeeRequestDto();
		//invalidRequest.setEmail(email);

		// call method expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> employeeService.createEmployee(email));

		// check results
		assertEquals(ex.getMessage(), "Person with given email is already an employee.");
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
	}

	// check if creating employee given person who does not yet exist throws exception
	@Test
	public void testInvalidCreateEmployeeForMissingPerson() {
		
		String email = "finnigan@mail.com";
		// when searching for a person using an email, return null since the person does not exist
		when(personRepo.findPersonByEmail(any(String.class))).thenAnswer(x -> null);
		// create the employee request and tie it to the existing person
		EmployeeRequestDto invalidRequest = new EmployeeRequestDto();
		invalidRequest.setEmail(email);

		// call method expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> employeeService.createEmployee(email));

		// check results
		assertEquals(ex.getMessage(), "Person with given email not found.");
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
	}
	
	// test delete employee
	@Test
	public void testFireEmployee() {
		// setup: create an employee to delete
		Employee smith = new Employee();
		int id = smith.getPersonRoleId();
		
		// Mock: if looking for smith using their ID, return smith
		when(employeeRepo.findEmployeeByPersonRoleId(id)).thenAnswer(x -> smith);
		
		// call method
		Employee returnedEmployee = employeeService.fireEmployee(id);
		
		// check results
		assertNotNull(returnedEmployee);
		assertEquals(id, returnedEmployee.getPersonRoleId());
		verify(employeeRepo,times(1)).delete(smith);
	}
	
	// test invalid delete employee - they are not found
	@Test
	public void testInvalidFireEmployee() {
		// Mock: if searching using an invalid employee id return null
		when(employeeRepo.findEmployeeByPersonRoleId(any(int.class))).thenAnswer(x -> null);
		
		// call method, expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> employeeService.fireEmployee(Integer.MAX_VALUE));

		// check results
		assertEquals(ex.getMessage(), "Employee with given ID not found.");
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
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
		
		// Mock: if searching for all employees return a list of employees
		when(employeeRepo.findAll()).thenAnswer(x -> hobbits);
		
		// call method
		List<Employee> employees = employeeService.getAllEmployees();
		
		//check results
		assertNotNull(employees);
		assertEquals(employees.get(0).getPersonRoleId(),bagginsID);
		assertEquals(employees.get(1).getPersonRoleId(),smeagolsID);
	}
}

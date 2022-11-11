package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class TestEmployeeService {

	// Replace the repository with a "mock" that exposes the same interface
	@Mock
	EmployeeRepository employeeRepo;
	
	// Get a service that uses the mock repository
	@InjectMocks
	EmployeeService employeeService;
	
	@Test
	public void testGetEmployeeById() {
		// Tell the mocked repository how to behave
		final int id = 1;
		final Employee hannah = new Employee();
		hannah.setPersonRoleId(id);
		when(employeeRepo.findEmployeeByPersonRoleId(id)).thenAnswer((InvocationOnMock invocation) -> hannah);
		
		// Test that the service behaves properly
		Employee employee = employeeService.getEmployeeById(id);
		
		assertNotNull(employee);
		assertEquals(id, employee.getPersonRoleId());
	}
	
	@Test
	public void testGetEmployeeByInvalidId() {
		final int invalidId = 99;
		
		// Mock: if asking for a employee with invalid ID, return null
		when(employeeRepo.findEmployeeByPersonRoleId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
		
		// call method, and obtain resulting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class, () -> employeeService.getEmployeeById(invalidId));
		
		// check results
		assertEquals("Employee not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	@Test
	public void testCreateEmployee() {
		// Mock: just return the Employee with no modification
		when(employeeRepo.save(any(Employee.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		// test set up
		final Employee finn = new Employee();
		int id = finn.getPersonRoleId();
		
		// call method
		Employee returnedEmployee = employeeService.createEmployee(finn);
		
		// check results
		assertNotNull(returnedEmployee);
		assertEquals(id, returnedEmployee.getPersonRoleId());
		// Check that the service actually saved the employee
		verify(employeeRepo, times(1)).save(finn);
	}
	
}

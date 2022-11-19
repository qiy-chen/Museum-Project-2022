package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Person;

@SpringBootTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private PersonRepository personRepository;

	@AfterEach
	public void clearDatabase() {
	  employeeRepository.deleteAll();
		personRepository.deleteAll();
		
	}
	
	@Test
	public void testPersistAndLoadEmployee() {
		// Create object
		Employee aEmployee = new Employee();

		// Save object
		aEmployee = employeeRepository.save(aEmployee);
		int EmployeeId = aEmployee.getPersonRoleId();

		// Read object from database
		aEmployee = null;
		aEmployee = employeeRepository.findEmployeeByPersonRoleId(EmployeeId);

		// Assert that object has correct attributes
		assertNotNull(aEmployee);
		assertEquals(EmployeeId, aEmployee.getPersonRoleId());
	}
	
	@Test
	public void testEmployeeToArtworkReference() {
		// Create object
		
		String email = "sandy@hotmail.com";
		Person sandy = new Person();
		sandy.setEmail(email);
		sandy = personRepository.save(sandy);
		
		Employee aEmployee = new Employee();
		aEmployee.setPerson(sandy);

		// Update object
		aEmployee = employeeRepository.save(aEmployee);
		int employeeID = aEmployee.getPersonRoleId();

		// Read object from database
		aEmployee = employeeRepository.findEmployeeByPersonRoleId(employeeID);

		// Assert that object has correct attributes
		assertNotNull(aEmployee);
		assertEquals(employeeID, aEmployee.getPersonRoleId());
		assertEquals(email,aEmployee.getPerson().getEmail());
	}
	
	
}

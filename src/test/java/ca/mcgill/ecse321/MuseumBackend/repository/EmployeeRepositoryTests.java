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
		personRepository.deleteAll();
		employeeRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadEmployee() {
		// Create object
		Employee aEmployee = new Employee();

		// Save object
		employeeRepository.save(aEmployee);
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
		Employee aEmployee = new Employee();
		employeeRepository.save(aEmployee); // save before adding art so that it is present for the foreign key when saving the artwork
		int employeeID = aEmployee.getPersonRoleId();
		
		
		String email = "sandy@hotmail.com";
		Person sandy = new Person();
		sandy.addPersonRole(aEmployee);
		sandy.setEmail(email);
		personRepository.save(sandy);
		aEmployee.setPerson(sandy);

		// Update object
		employeeRepository.save(aEmployee);

		// Read object from database
		aEmployee = employeeRepository.findEmployeeByPersonRoleId(employeeID);

		// Assert that object has correct attributes
		assertNotNull(aEmployee);
		assertEquals(employeeID, aEmployee.getPersonRoleId());
		assertEquals(email,aEmployee.getPerson().getEmail());
	}
	
	
}

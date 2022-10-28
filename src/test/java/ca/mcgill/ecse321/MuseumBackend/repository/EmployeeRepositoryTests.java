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
	@Autowired
	private MuseumRepository museumRepository;

	@AfterEach
	public void clearDatabase() {
		employeeRepository.deleteAll();
		personRepository.deleteAll();
		museumRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadEmployee() {
		// Create object
		int id = 125;
		Employee aEmployee = new Employee();
		aEmployee.setPersonRoleId(id);

		// Save object
		employeeRepository.save(aEmployee);
		int EmployeeId = aEmployee.getPersonRoleId();

		// Read object from database
		aEmployee = null;
		aEmployee = employeeRepository.findEmployeeByPersonRoleId(id);

		// Assert that object has correct attributes
		assertNotNull(aEmployee);
		assertEquals(EmployeeId, aEmployee.getPersonRoleId());
	}
	
	@Test
	public void testEmployeeToArtworkReference() {
		// Create object
		int id = 1;
		Employee aEmployee = new Employee();
		aEmployee.setPersonRoleId(id);
		employeeRepository.save(aEmployee); // save before adding art so that it is present for the foreign key when saving the artwork
		
		// create references
		Museum aMuseum = new Museum(21);
		museumRepository.save(aMuseum);
		
		String email = "sandy@hotmail.com";
		Person sandy = new Person();
		sandy.addPersonRole(aEmployee);
		sandy.setMuseum(aMuseum);
		sandy.setEmail(email);
		personRepository.save(sandy);
		aEmployee.setPerson(sandy);

		// Update object
		employeeRepository.save(aEmployee);
		int employeeID = aEmployee.getPersonRoleId();

		// Read object from database
		aEmployee = employeeRepository.findEmployeeByPersonRoleId(employeeID);

		// Assert that object has correct attributes
		assertNotNull(aEmployee);
		assertEquals(employeeID, aEmployee.getPersonRoleId());
		assertEquals(email,aEmployee.getPerson().getEmail());
	}
	
	
}

package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Person;

@SpringBootTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;
	private PersonRepository personRepository;

	@AfterEach
	public void clearDatabase() {
		employeeRepository.deleteAll();
		personRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadEmployee() {
		// Create object
		Employee anakin = new Employee();

		// Save object
		anakin = employeeRepository.save(anakin);
		int id = anakin.getPersonRoleId();

		// Read object from database
		anakin = employeeRepository.findEmployeeByPersonRoleId(id);

		// Assert that object has correct attributes
		assertNotNull(anakin);
		assertEquals(id, anakin.getPersonRoleId());
	}
	
	@Test
	public void testEmployeeToPersonReference() {
		// Create object
		String name = "anakin skywalker";
		Person aPerson = new Person();
		Employee anakin = new Employee();
		anakin.setPerson(aPerson);
		aPerson.setName(name);

		// Save object
		anakin = employeeRepository.save(anakin);
		aPerson = personRepository.save(aPerson);
		int id = anakin.getPersonRoleId();

		// Read object from database
		anakin = employeeRepository.findEmployeeByPersonRoleId(id);

		// Assert that object has correct attributes
		assertNotNull(anakin);
		assertEquals(id, anakin.getPersonRoleId());
		assertEquals(name, anakin.getPerson().getName());
	}
	
	
}

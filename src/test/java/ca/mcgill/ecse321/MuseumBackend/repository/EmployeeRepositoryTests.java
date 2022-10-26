package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.User;

@SpringBootTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@AfterEach
	public void clearDatabase() {
		employeeRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadEmployee() {
		// Create object
		String name = "anakin skywalker";
		User aUser = new User();
		Employee anakin = new Employee();
		aUser.addUserRole(anakin);
		aUser.setName(name);

		// Save object
		anakin = employeeRepository.save(anakin);
		UUID id = anakin.getUserRoleId();

		// Read object from database
		anakin = employeeRepository.findEmployeeByAUserRoleId(id);

		// Assert that object has correct attributes
		assertNotNull(anakin);
		assertEquals(id, anakin.getUserRoleId());
		assertEquals(name, anakin.getUser(0).getName());
	}
	
}

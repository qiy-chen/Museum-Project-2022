package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.User;

@SpringBootTest
public class AdminRepositoryTests {
	@Autowired
	private AdminRepository adminRepository;

	@AfterEach
	public void clearDatabase() {
		adminRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadAdmin() {
		// Create object
		String name = "anakin skywalker";
		User aUser = new User();
		Admin anakin = new Admin();
		aUser.addUserRole(anakin);
		aUser.setName(name);

		// Save object
		anakin = adminRepository.save(anakin);
		UUID id = anakin.getUserRoleId();

		// Read object from database
		anakin = adminRepository.findAdminByAUserRoleId(id);

		// Assert that object has correct attributes
		assertNotNull(anakin);
		assertEquals(id, anakin.getUserRoleId());
		assertEquals(name, anakin.getUser(0).getName());
	}
}

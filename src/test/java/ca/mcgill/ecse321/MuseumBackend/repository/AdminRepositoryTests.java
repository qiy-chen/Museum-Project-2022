package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.User;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.PersonRole;
import ca.mcgill.ecse321.MuseumBackend.model.Person;


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
		String aEmail = "emma.k@gmail.com";
		String aPassword = "12345";
		int aMuseumId = 123;
		Museum aMuseum = new Museum(aMuseumId);
		Person aPerson = new Person(aEmail, aPassword, name, aMuseum);
		
		PersonRole anakin = new Admin();
		anakin.setPerson(aPerson);
		anakin.setPersonRoleId(324);
		
		adminRepository.save(anakin);
		int adminId = anakin.getPersonRoleId();
		anakin = null;
		anakin = adminRepository.findAdminByAPersonRoleID(adminId);
		assertNotNull(anakin);
		assertEquals(adminId, anakin.getPersonRoleId());
	}
}

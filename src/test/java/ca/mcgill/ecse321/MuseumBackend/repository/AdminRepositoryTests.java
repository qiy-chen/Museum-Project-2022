package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Person;



@SpringBootTest
public class AdminRepositoryTests {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private MuseumRepository museumRepository;

	@Autowired
	private PersonRepository personRepository;
	@AfterEach
	public void clearDatabase() {
		adminRepository.deleteAll();
		personRepository.deleteAll();
		museumRepository.deleteAll();

	}

	@Test
	public void testPersistAndLoadAdmin() {
		// Create object
		String email = "samuel.faubert@mail.mcgill.ca";
		String name = "Samuel Faubert";
		String password = "MarwanisC00l";
		Person samuel = new Person();
		samuel.setEmail(email);
		samuel.setName(name);
		samuel.setPassword(password);
		samuel = personRepository.save(samuel);
		samuel.addPersonRole(new Admin());
		samuel.getPersonRole(0).setPersonRoleId(324);
		Admin anakin = (Admin) samuel.getPersonRole(0);
		anakin = adminRepository.save(anakin);
		int adminId = anakin.getPersonRoleId();
		anakin = null;
		anakin = adminRepository.findAdminByPersonRoleId(adminId);
		assertNotNull(anakin);
		assertEquals(adminId, anakin.getPersonRoleId());
	}
}

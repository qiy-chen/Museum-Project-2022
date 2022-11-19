package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import ca.mcgill.ecse321.MuseumBackend.dto.AdminResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.AdminRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
/**
 * @Author Jeanine Looman
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // set random port
public class TestAdminIntegration {

	@Autowired
	private TestRestTemplate client;
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private PersonRepository personRepo;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		personRepo.deleteAll();
		adminRepo.deleteAll();
	}

	@Test
	public void testCreateAndGetAndDeleteAdmin() {
		int id = testCreateAdmin();
		testGetAdmin(id);
		testDeleteAdmin(id);
	}

	private int testCreateAdmin() {

		// setup - first create and save person that will get the role admin
		Person person = new Person();
		String email = "obi@kenobi.com";
		person.setEmail(email);
		personRepo.save(person);

		// call method: create a new admin
		ResponseEntity<AdminDto> response = client.postForEntity("/admin", new AdminDto(email), AdminDto.class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals(email, response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id > 0, "Response has valid ID");

		return response.getBody().id;
	}

	private void testGetAdmin(int id) {

		// call method: get the admin by their id
		ResponseEntity<AdminDto> response = client.getForEntity("/admin/" + id, AdminDto.class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("obi@kenobi.com", response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id == id, "Response has correct ID");
	}

	@Test
	public void testCreateInvalidAdmin() {
		ResponseEntity<String> response = client.postForEntity("/admin", new AdminDto("   "), String.class);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
	}

	@Test
	public void testGetInvalidAdmin() {
		ResponseEntity<String> response = client.getForEntity("/admin/" + Integer.MAX_VALUE, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
		assertEquals("Admin not found.", response.getBody(), "Response has correct error message");
	}

	// test fire admin
	private void testDeleteAdmin(int id) {
		client.delete("/admin/" + id);
		try {
			client.getForEntity("/admin/" + id, AdminResponseDto.class);
			fail("Person was found!");
		} catch (RestClientException | IllegalArgumentException e) {
		}
	}
}

class AdminDto {
	public int id;
	public String email;

	// Need default constructor so that Jackson can instantiate the object
	public AdminDto() {
	}

	public AdminDto(String email) {
		this.email = email;
	}
}

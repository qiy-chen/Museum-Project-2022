package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.MuseumBackend.repository.AdminRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //set random port
public class TestAdminIntegration {

	@Autowired
	private TestRestTemplate client;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		adminRepo.deleteAll();
	}
	
	@Test
	public void testCreateAndGetAdmin() {
		int id = testCreateAdmin();
		testGetAdmin(id);
	}
	
	private int testCreateAdmin() {
		ResponseEntity<AdminDto> response = client.postForEntity("/admin", new AdminDto("obi@kenobi.com"), AdminDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("obi@kenobi.com", response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id > 0, "Response has valid ID");
		
		return response.getBody().id;
	}
	
	private void testGetAdmin(int id) {
		ResponseEntity<AdminDto> response = client.getForEntity("/admin/" + id, AdminDto.class);
		
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
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	}
	
	@Test
	public void testGetInvalidAdmin() {
		ResponseEntity<String> response = client.getForEntity("/admin/" + Integer.MAX_VALUE, String.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
		assertEquals("Admin not found.", response.getBody(), "Response has correct error message");
	}
}

class AdminDto {
	public int id;
	public String email;
	
	// Need default constructor so that Jackson can instantiate the object
	public AdminDto() {}
	
	public AdminDto(String email) {
		this.email = email;
	}
}

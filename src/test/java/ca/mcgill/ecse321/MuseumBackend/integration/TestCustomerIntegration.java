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

import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //set random port
public class TestCustomerIntegration {

	@Autowired
	private TestRestTemplate client;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		customerRepo.deleteAll();
	}
	
	@Test
	public void testCreateAndGetCustomer() {
		int id = testCreateCustomer();
		testGetCustomer(id);
	}
	
	private int testCreateCustomer() {
		ResponseEntity<CustomerDto> response = client.postForEntity("/customer", new CustomerDto("obi@kenobi.com"), CustomerDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("obi@kenobi.com", response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id > 0, "Response has valid ID");
		
		return response.getBody().id;
	}
	
	private void testGetCustomer(int id) {
		ResponseEntity<CustomerDto> response = client.getForEntity("/customer/" + id, CustomerDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("obi@kenobi.com", response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id == id, "Response has correct ID");
	}
	
	@Test
	public void testCreateInvalidCustomer() {
		ResponseEntity<String> response = client.postForEntity("/customer", new CustomerDto("   "), String.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
	}
	
	@Test
	public void testGetInvalidCustomer() {
		ResponseEntity<String> response = client.getForEntity("/customer/" + Integer.MAX_VALUE, String.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
		assertEquals("Customer not found.", response.getBody(), "Response has correct error message");
	}
}

class CustomerDto {
	public int id;
	public String email;
	
	// Need default constructor so that Jackson can instantiate the object
	public CustomerDto() {}
	
	public CustomerDto(String email) {
		this.email = email;
	}
}

package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import ca.mcgill.ecse321.MuseumBackend.dto.CustomerResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // set random port
public class TestCustomerIntegration {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private PersonRepository personRepo;

	@BeforeEach
	public void wipeDatabase() {
		personRepo.deleteAll();
		customerRepo.deleteAll();
	}
	
	@AfterEach
	public void clearDatabase() {
		personRepo.deleteAll();
		customerRepo.deleteAll();
	}

	@Test
	public void testCreateAndGetCustomer() {
		int id = testCreateCustomer("obi@kenobi.com");
		testGetCustomer(id, "obi@kenobi.com");
	}

	private int testCreateCustomer(String email) {

		// setup - first create and save person that will get the role customer
		Person person = new Person();
		person.setEmail(email);
		personRepo.save(person);

		// call method: create a new customer
		ResponseEntity<CustomerDto> response = client.postForEntity("/customer", new CustomerDto(email),
				CustomerDto.class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals(email, response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id > 0, "Response has valid ID");

		return response.getBody().id;
	}

	private void testGetCustomer(int id, String email) {

		// call method: get the customer by their id
		ResponseEntity<CustomerDto> response = client.getForEntity("/customer/" + id, CustomerDto.class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals(email, response.getBody().email, "Response has correct email");
		assertTrue(response.getBody().id == id, "Response has correct ID");
	}

	@Test
	public void testCreateInvalidCustomer() {
		ResponseEntity<String> response = client.postForEntity("/customer", new CustomerDto("   "), String.class);
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
	}

	@Test
	public void testGetInvalidCustomer() {
		getInvalidCustomer(Integer.MAX_VALUE);
	}
	
	public void getInvalidCustomer(int invalidID) {
		
		ResponseEntity<String> response = client.getForEntity("/customer/" + invalidID, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
		assertEquals("Customer not found.", response.getBody(), "Response has correct error message");
	}

	// test get all customers
	@Test
	public void testGetAllCustomers() {

		// setup -  make two customers
		int bilboID = testCreateCustomer("bilbo@baggins.com");
		int gandalfID = testCreateCustomer("gandalf@grey.com");
		
		// call method: get the customer by their id
		ResponseEntity<CustomerDto[]> response = client.getForEntity("/customers", CustomerDto[].class);

		// check response
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		CustomerDto[] customers = response.getBody();
		assertEquals(2, customers.length, "Response has all customers");
		assertEquals(bilboID, customers[0].id, "Correct ID");
		assertEquals(gandalfID, customers[1].id, "Correct ID");
	}
	
	// test fire customer
	@Test
	public void testDeleteCustomer() {
		
		// setup - make customer and check they are saved
		String email = "bilbo@baggins.com";
		int bilboID = testCreateCustomer(email);
		testGetCustomer(bilboID, email);
		
		// fire the customer
		testDeleteCustomer(bilboID);
		
		//check that they cannot be found
		getInvalidCustomer(bilboID);
	}
	
	private void testDeleteCustomer(int id) {
        client.delete("/customer/"+id);
        try {
            client.getForEntity("/customer/"+id,CustomerResponseDto.class);
            fail("Person was found!");
        }catch (RestClientException|IllegalArgumentException e) {}
    }
}

class CustomerDto {
	public int id;
	public String email;

	// Need default constructor so that Jackson can instantiate the object
	public CustomerDto() {
	}

	public CustomerDto(String email) {
		this.email = email;
	}
}

package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.MuseumBackend.exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

	// Replace the repository with a "mock" that exposes the same interface
	@Mock
	CustomerRepository customerRepo;
	@Mock
	PersonRepository personRepo;

	// Get a service that uses the mock repository
	@InjectMocks
	CustomerService customerService;
	@InjectMocks
	CustomerService personService;

	@Test
	public void testGetCustomerById() {
		// Tell the mocked repository how to behave
		final int id = 1;
		final Customer hannah = new Customer();
		hannah.setPersonRoleId(id);
		when(customerRepo.findCustomerByPersonRoleId(id)).thenAnswer((InvocationOnMock invocation) -> hannah);

		// Test that the service behaves properly
		Customer customer = customerService.getCustomerById(id);

		assertNotNull(customer);
		assertEquals(id, customer.getPersonRoleId());
	}

	@Test
	public void testGetCustomerByInvalidId() {
		final int invalidId = 99;

		// Mock: if asking for a customer with invalid ID, return null
		when(customerRepo.findCustomerByPersonRoleId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

		// call method, and obtain resulting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> customerService.getCustomerById(invalidId));

		// check results
		assertEquals("Customer not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}

	@Test
	public void testCreateCustomer() {
		// Mock: just return the Customer with no modification
		when(customerRepo.save(any(Customer.class)))
				.thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

		// test set up - create a person
		Person person = new Person();
		String email = "finnigan@mail.com";
		String name = "Finn Igan";
		person.setEmail(email);
		person.setName(name);
		// when searching for person using email, return person
		when(personRepo.findPersonByEmail(email)).thenAnswer((InvocationOnMock invocation) -> person);
		// create the customer request and tie it to the existing person
		CustomerRequestDto finn = new CustomerRequestDto();
		finn.setEmail(email);

		// call method
		CustomerResponseDto returnedCustomer = customerService.createCustomer(email);

		// check results
		assertNotNull(returnedCustomer);
		assertEquals(email, returnedCustomer.getPerson().getEmail());
		// Check that the service actually saved the customer
		// verify(customerRepo, times(1)).save();
	}

	// check if creating customer given person who is already an customer throws
	// exception
	@Test
	public void testInvalidCreateExistingCustomer() {

		// test set up - create a person who is already an customer
		Person person = new Person();
		String email = "finnigan@mail.com";
		String name = "Finn Igan";
		person.setEmail(email);
		person.setName(name);
		Customer existingCustomer = new Customer();
		person.addPersonRole(existingCustomer);
		// when searching for person using email, return person
		when(personRepo.findPersonByEmail(email)).thenAnswer((InvocationOnMock invocation) -> person);
		// create the customer request and tie it to the existing person
		// CustomerRequestDto invalidRequest = new CustomerRequestDto();
		// invalidRequest.setEmail(email);

		// call method expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> customerService.createCustomer(email));

		// check results
		assertEquals(ex.getMessage(), "Person with given email is already a customer.");
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
	}

	// check if creating customer given person who does not yet exist throws
	// exception
	@Test
	public void testInvalidCreateCustomerForMissingPerson() {

		String email = "finnigan@mail.com";
		// when searching for a person using an email, return null since the person does
		// not exist
		when(personRepo.findPersonByEmail(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null);
		// create the customer request and tie it to the existing person
		CustomerRequestDto invalidRequest = new CustomerRequestDto();
		invalidRequest.setEmail(email);

		// call method expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> customerService.createCustomer(email));

		// check results
		assertEquals(ex.getMessage(), "Person not found.");
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	// test delete customer
		@Test
		public void testDeleteCustomer() {
			// setup: create an customer to delete
			Person person = new Person();
			String email = "finnigan@mail.com";
			String name = "Finn Igan";
			person.setEmail(email);
			person.setName(name);
			Customer smith = new Customer();
			int id = smith.getPersonRoleId();
			smith.setPerson(person);
			
			// Mock: if looking for smith using their ID, return smith
			when(customerRepo.findCustomerByPersonRoleId(id)).thenAnswer(x -> smith);
			
			// call method
			customerService.deleteCustomer(id);
			
			// check results
			verify(customerRepo,times(1)).delete(smith);
		}

	// test invalid delete customer - they are not found
	@Test
	public void testInvalidDeleteCustomer() {
		// Mock: if searching using an invalid customer id return null
		when(customerRepo.findCustomerByPersonRoleId(any(int.class))).thenAnswer(x -> null);

		// call method, expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> customerService.deleteCustomer(Integer.MAX_VALUE));

		// check results
		assertEquals(ex.getMessage(), "Customer not found.");
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}

	// test get all customers
	@Test
	public void testGetAllCustomers() {

		// setup: make list of customers to find
		Customer baggins = new Customer();
		Customer smeagol = new Customer();
		int bagginsID = baggins.getPersonRoleId();
		int smeagolsID = smeagol.getPersonRoleId();

		ArrayList<Customer> hobbits = new ArrayList<>();
		hobbits.add(baggins);
		hobbits.add(smeagol);

		// Mock: if searching for all customers return a list of customers
		when(customerRepo.findAll()).thenAnswer(x -> hobbits);

		// call method
		List<Customer> customers = customerService.getAllCustomers();

		// check results
		assertNotNull(customers);
		assertEquals(customers.get(0).getPersonRoleId(), bagginsID);
		assertEquals(customers.get(1).getPersonRoleId(), smeagolsID);
	}
}

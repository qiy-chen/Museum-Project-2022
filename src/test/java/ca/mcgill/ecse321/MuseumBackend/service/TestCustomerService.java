package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
		when(customerRepo.save(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

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

	// check if creating customer given person who is already an customer throws exception
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
		//CustomerRequestDto invalidRequest = new CustomerRequestDto();
		//invalidRequest.setEmail(email);

		// call method expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> customerService.createCustomer(email));

		// check results
		assertEquals(ex.getMessage(), "Person with given email is already a customer.");
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
	}

	// check if creating customer given person who does not yet exist throws exception
	@Test
	public void testInvalidCreateCustomerForMissingPerson() {
		
		String email = "finnigan@mail.com";
		// when searching for a person using an email, return null since the person does not exist
		when(personRepo.findPersonByEmail(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null);
		// create the customer request and tie it to the existing person
		CustomerRequestDto invalidRequest = new CustomerRequestDto();
		invalidRequest.setEmail(email);

		// call method expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> customerService.createCustomer(email));

		// check results
		assertEquals(ex.getMessage(), "Person with given email not found.");
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
	}
}

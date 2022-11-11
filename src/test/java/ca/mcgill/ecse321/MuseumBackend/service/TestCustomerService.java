package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

	// Replace the repository with a "mock" that exposes the same interface
	@Mock
	CustomerRepository customerRepo;
	
	// Get a service that uses the mock repository
	@InjectMocks
	CustomerService customerService;
	
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
		MuseumBackendException ex = assertThrows(MuseumBackendException.class, () -> customerService.getCustomerById(invalidId));
		
		// check results
		assertEquals("Customer not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	@Test
	public void testCreateCustomer() {
		// Mock: just return the Customer with no modification
		when(customerRepo.save(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		// test set up
		final Customer finn = new Customer();
		int id = finn.getPersonRoleId();
		
		// call method
		Customer returnedCustomer = customerService.createCustomer(finn);
		
		// check results
		assertNotNull(returnedCustomer);
		assertEquals(id, returnedCustomer.getPersonRoleId());
		// Check that the service actually saved the customer
		verify(customerRepo, times(1)).save(finn);
	}
	
}

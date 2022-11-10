package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepo;
	
	@Transactional
	public Customer getCustomerById(int id) {
		Customer customer = customerRepo.findCustomerByPersonRoleId(id);
		if (customer == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Customer not found.");
		}
		return customer;
	}
	
	@Transactional
	public Customer createCustomer(Customer customer) {
		customer = customerRepo.save(customer);
		return customer;
	}
}

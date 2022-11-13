package ca.mcgill.ecse321.MuseumBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
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
	
	// TO DO check customer attributes, add ID
	@Transactional
	public Customer createCustomer(Customer customer) {
		customer = customerRepo.save(customer);
		return customer;
	}
	
	// delete customer
	@Transactional
	public Customer deleteCustomer(int ID){
	    Customer customer = customerRepo.findCustomerByPersonRoleId(ID);
	    if(customer == null) 
	    	throw new NullPointerException("Customer not found");
	    customerRepo.delete(customer); 
	    return customer;
	}

	// get all customers
	public List<Customer> getAllCustomers() {
		return toList(customerRepo.findAll());
	}
	
	// convert ArrayList to List
		private <T> List<T> toList(Iterable<T> iterable){
			List<T> resultList = new ArrayList<T>();
			for (T t : iterable) {
				resultList.add(t);
			}
			return resultList;
		}
}

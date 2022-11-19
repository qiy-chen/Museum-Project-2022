package ca.mcgill.ecse321.MuseumBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.repository.CustomerRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	PersonRepository personRepo;

	// get customer by their ID
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
	public CustomerResponseDto createCustomer(String email) {
		// check if the person with the given email already exists, else throw error (we
		// don't want to create people from the role end)
		Person person = personRepo.findPersonByEmail(email);
		if (person == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Person not found.");
		}
		if (person.isCustomer()) {
			throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Person with given email is already a customer.");
		}
		Customer Customer = new Customer();
		Customer.setPerson(person);
		Customer = customerRepo.save(Customer);
		return new CustomerResponseDto(Customer);
	}

	// delete customer
	@Transactional
	public Customer deleteCustomer(int ID) {
		Customer customer = customerRepo.findCustomerByPersonRoleId(ID);
		if (customer == null)
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Customer not found.");
		customerRepo.delete(customer);
		customer.delete();
		return customer;
	}

	// get all customers
	public List<Customer> getAllCustomers() {
		return toList(customerRepo.findAll());
	}

	// find all tickets for one customer
	@Transactional
	public List<Ticket> getTicketsForCustomer(int id) {
		Customer customer = customerRepo.findCustomerByPersonRoleId(id);
		if (customer == null)
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Customer not Found");
		return customer.getTickets();
	}

	// find all loans for one customer
	@Transactional
	public List<Loan> getLoansForCustomer(int id) {
		Customer customer = customerRepo.findCustomerByPersonRoleId(id);
		if (customer == null)
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Customer not Found");
		return customer.getLoans();
	}

	// convert ArrayList to List
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}

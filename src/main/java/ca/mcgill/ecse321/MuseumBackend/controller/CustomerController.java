package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.MuseumBackend.dto.CustomerRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	// find customer by customer ID
	@GetMapping("/customer/{id}")
	public ResponseEntity<CustomerResponseDto> getCustomerByID(@PathVariable int id) {
		Customer customer = customerService.getCustomerById(id);
		return new ResponseEntity<CustomerResponseDto>(new CustomerResponseDto(customer), HttpStatus.OK);
	}
	
	// get all customers
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		ArrayList<CustomerResponseDto> customerDtos = new ArrayList<>();
		for (Customer e : customers)
			customerDtos.add(new CustomerResponseDto(e));
		return new ResponseEntity<List<CustomerResponseDto>>(toList(customerDtos), HttpStatus.OK);
	}
	
	// create new customer
	@PostMapping("/customer")
	public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto request) {
		CustomerResponseDto response = customerService.createCustomer(request.getEmail());
		return new ResponseEntity<CustomerResponseDto>(response, HttpStatus.CREATED);
	}

	// delete customer by ID
	@DeleteMapping("/customer/delete")
	public ResponseEntity<CustomerResponseDto> deleteCustomer(@RequestParam int id) {
		CustomerResponseDto response = new CustomerResponseDto(customerService.deleteCustomer(id));
		return new ResponseEntity<CustomerResponseDto>(response, HttpStatus.OK);
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

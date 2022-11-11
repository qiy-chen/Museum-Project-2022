package ca.mcgill.ecse321.MuseumBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.MuseumBackend.dto.CustomerRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customer/{name}")
	public ResponseEntity<CustomerResponseDto> getCustomerByID(@PathVariable int ID) {
		Customer customer = customerService.getCustomerById(ID);
		return new ResponseEntity<CustomerResponseDto>(new CustomerResponseDto(customer), HttpStatus.OK);
	}
	
	@PostMapping("/customer")
	public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto request) {
		Customer customerToCreate = request.toModel();
		Customer createdCustomer = customerService.createCustomer(customerToCreate);
		CustomerResponseDto response = new CustomerResponseDto(createdCustomer);
		return new ResponseEntity<CustomerResponseDto>(response, HttpStatus.CREATED);
	}
}

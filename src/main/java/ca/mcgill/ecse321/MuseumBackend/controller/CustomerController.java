package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.CustomerRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.CustomerResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.LoanResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

	// create new customer
	@PostMapping("/customer")
	public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto request) {
		CustomerResponseDto response = customerService.createCustomer(request.getEmail());
		return new ResponseEntity<CustomerResponseDto>(response, HttpStatus.CREATED);
	}

	// delete customer by ID
	@DeleteMapping("/customer/{id}")
	public void deleteCustomer(@PathVariable int id) {
		customerService.deleteCustomer(id);
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

	// get all tickets for customer
	@GetMapping("/customer/tickets/{id}")
	public ResponseEntity<List<TicketResponseDto>> getTicketsForCustomer(@PathVariable int id) {
		List<Ticket> tickets = customerService.getTicketsForCustomer(id);
		ArrayList<TicketResponseDto> ticketDtos = new ArrayList<>();
		for (Ticket s : tickets)
			ticketDtos.add(new TicketResponseDto(s));
		return new ResponseEntity<List<TicketResponseDto>>(toList(ticketDtos), HttpStatus.OK);
	}

	// get all loans for customer
	@GetMapping("/customer/loans/{id}")
	public ResponseEntity<List<LoanResponseDto>> getLoansForCustomer(@PathVariable int id) {
		List<Loan> loans = customerService.getLoansForCustomer(id);
		ArrayList<LoanResponseDto> loanDtos = new ArrayList<>();
		for (Loan s : loans)
			loanDtos.add(new LoanResponseDto(s));
		return new ResponseEntity<List<LoanResponseDto>>(toList(loanDtos), HttpStatus.OK);
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

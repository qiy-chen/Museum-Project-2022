package ca.mcgill.ecse321.MuseumBackend.dto;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;

import ca.mcgill.ecse321.MuseumBackend.service.PersonService;
import ca.mcgill.ecse321.MuseumBackend.service.LoanService;

public class CustomerRequestDto {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private LoanService loanService;

	// attributes
	private String email;
	private int[] loanIDs;
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPersonID() {
		return this.email;
	}
	
	public void setCustomerLoanIDs(int[] loanIDs) {
		this.loanIDs = loanIDs;
	}
	
	public int[] getCustomerLoans() {
		return this.loanIDs;
	}
	
	public Customer toModel() {
		Customer customer = new Customer();
		Person person = personService.getPersonByEmail(this.email);
		customer.setPerson(person);
		for (int loanID: this.loanIDs) {
			Loan loan = loanService.getLoanById(loanID);
			customer.addLoan(loan);
		}
		return customer;
	}
}

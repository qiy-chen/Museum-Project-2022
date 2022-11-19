package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;

public class CustomerResponseDto {

	private int id;
	private PersonDto person;
	private List<LoanResponseDto> loans;
	private String email;
	
	public CustomerResponseDto() {}
	public CustomerResponseDto(Customer customer) {
		this.id = customer.getPersonRoleId();
		this.person = new PersonDto(customer.getPerson());
		this.email = this.person.getEmail();
		for (Loan loan : customer.getLoans()) {
			this.loans.add(new LoanResponseDto(loan));
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PersonDto getPerson() {
		return person;
	}

	public void setPerson(PersonDto person) {
		this.person = person;
	}

	public List<LoanResponseDto> getLoans() {
		return loans;
	}

	public void setLoans(List<LoanResponseDto> loans) {
		this.loans = loans;
	}
	
}

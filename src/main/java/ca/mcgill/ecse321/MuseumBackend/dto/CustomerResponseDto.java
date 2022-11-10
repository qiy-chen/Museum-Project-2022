package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;

public class CustomerResponseDto {

	private int id;
	private PersonDto person;
	private List<LoanDto> loans;
	
	public CustomerResponseDto(Customer customer) {
		this.id = customer.getPersonRoleId();
		this.person = new PersonDto(customer.getPerson());
		for (Loan loan : customer.getLoans()) {
			this.loans.add(new LoanDto(loan));
		}
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

	public List<LoanDto> getLoans() {
		return loans;
	}

	public void setLoans(List<LoanDto> loans) {
		this.loans = loans;
	}
	
}

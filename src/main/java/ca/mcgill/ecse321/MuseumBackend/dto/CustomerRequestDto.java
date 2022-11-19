package ca.mcgill.ecse321.MuseumBackend.dto;

public class CustomerRequestDto {

	// attributes
	private String email;
	public CustomerRequestDto() {}
	public int[] getLoanIDs() {
		return loanIDs;
	}


	public String getEmail() {
		return email;
	}

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
	
}

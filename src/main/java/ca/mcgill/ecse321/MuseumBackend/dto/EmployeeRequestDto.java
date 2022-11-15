package ca.mcgill.ecse321.MuseumBackend.dto;

public class EmployeeRequestDto {
	
	public int[] getShiftIDs() {
		return shiftIDs;
	}

	public void setShiftIDs(int[] shiftIDs) {
		this.shiftIDs = shiftIDs;
	}

	// attributes
	private String email;
	public String getEmail() {
		return email;
	}

	private int[] shiftIDs;
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPersonID() {
		return this.email;
	}
	
	public void setEmployeeShiftIDs(int[] shiftIDs) {
		this.shiftIDs = shiftIDs;
	}
	
	public int[] getEmployeeShifts() {
		return this.shiftIDs;
	}
	
}

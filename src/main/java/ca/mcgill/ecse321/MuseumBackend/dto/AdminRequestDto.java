package ca.mcgill.ecse321.MuseumBackend.dto;
/**
 * @Author Jeanine Looman
 */
public class AdminRequestDto {

	// attributes
	private String email;
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPersonEmail() {
		return this.email;
	}

}

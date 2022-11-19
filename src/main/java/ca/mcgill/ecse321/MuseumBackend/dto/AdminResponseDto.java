package ca.mcgill.ecse321.MuseumBackend.dto;


import ca.mcgill.ecse321.MuseumBackend.model.Admin;

public class AdminResponseDto {

	private int id;
	private PersonDto person;
	private String email;
	
	public AdminResponseDto(Admin admin) {
		this.id = admin.getPersonRoleId();
		this.person = new PersonDto(admin.getPerson());
		this.email = this.person.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}

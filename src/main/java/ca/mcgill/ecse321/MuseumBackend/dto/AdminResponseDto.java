package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Admin;

public class AdminResponseDto {

	private int id;
	private PersonDto person;
	
	public AdminResponseDto(Admin admin) {
		this.id = admin.getPersonRoleId();
		this.person = new PersonDto(admin.getPerson());
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

}

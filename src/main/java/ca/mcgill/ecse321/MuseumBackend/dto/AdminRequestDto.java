package ca.mcgill.ecse321.MuseumBackend.dto;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

import ca.mcgill.ecse321.MuseumBackend.service.PersonService;
import ca.mcgill.ecse321.MuseumBackend.service.ShiftService;

public class AdminRequestDto {
	
	@Autowired
	private PersonService personService;

	// attributes
	private String email;
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPersonID() {
		return this.email;
	}
	
	
	public Admin toModel() {
		Admin admin = new Admin();
		Person person = personService.getPersonByEmail(this.email);
		admin.setPerson(person);
		return admin;
	}
}

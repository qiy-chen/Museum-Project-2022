package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Person;

public class PersonDto {

	private String email;
	private String name;
	public PersonDto() {}
	
	public PersonDto(Person person) {
		this.email = person.getEmail();
		this.name = person.getName();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Person toModel() {
	  Person person = new Person();
	  person.setEmail(email);
	  person.setName(name);
	  return person;
	}
}

package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Person;

public class PersonDto {

	private String email;
	private String name;
	
	public PersonDto(Person person) {
		this.email = person.getEmail();
		this.name = person.getName();
	}
	public PersonDto() {}
	
	public Person toModel() {
	  Person person = new Person();
	  person.setEmail(email);
	  person.setName(name);
	  return person;
	}
}

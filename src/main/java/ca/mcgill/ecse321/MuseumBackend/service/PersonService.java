package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepo;
	
	@Transactional
	public Person getPersonByEmail(String email) {
		Person person = personRepo.findPersonByEmail(email);
		if (person == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Person not found.");
		}
		return person;
	}
	
	@Transactional
	public Person createPerson(Person person) {
		person = personRepo.save(person);
		return person;
	}
}

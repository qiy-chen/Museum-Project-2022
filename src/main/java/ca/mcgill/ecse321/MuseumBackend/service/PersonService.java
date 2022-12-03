package ca.mcgill.ecse321.MuseumBackend.service;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
	public List<Person> getPeople() {
		List<Person> people = new ArrayList<>();
		Iterable<Person> peopleIterable = personRepo.findAll();
		for(Person person : peopleIterable) {
			people.add(person);
		}
		return people;
	}
	@Transactional
	public Person createPerson(Person person) {
		person = personRepo.save(person);
		return person;
	}

	@Transactional
	public Person changePersonPassword(String email, String newPassword) {
		Person person = personRepo.findPersonByEmail(email);
		person.setPassword(newPassword);
		return person;
	}
	@Transactional
	public Person changePersonName(String email, String newName) {
		Person person = personRepo.findPersonByEmail(email);
		person.setName(newName);
		return person;
	}
	@Transactional
	public void deletePerson(String email) {
		Person person = personRepo.findPersonByEmail(email);
		personRepo.delete(person);
		person.delete();
	}
}

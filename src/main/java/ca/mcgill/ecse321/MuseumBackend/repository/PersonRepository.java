package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{

	Person findPersonByPersonId(int aPersonId);
	
}
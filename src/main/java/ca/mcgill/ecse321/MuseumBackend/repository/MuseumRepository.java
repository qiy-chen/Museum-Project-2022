package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;

public interface MuseumRepository extends CrudRepository<Museum, Integer>{

	Museum findMuseumByMuseumId(int aMuseumId);
	
}
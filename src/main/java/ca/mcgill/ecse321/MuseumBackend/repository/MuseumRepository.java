package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import org.springframework.data.repository.CrudRepository;

public interface MuseumRepository extends CrudRepository<Museum, Integer>{

	Museum findMuseumByMuseumId(int aMuseumId);
	
}
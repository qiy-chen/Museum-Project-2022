package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Display;

public interface DisplayRepository extends CrudRepository<Display, Integer>{

	Display findDisplayByRoomId(int aDisplayId);
	
}
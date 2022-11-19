package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Display;
import org.springframework.data.repository.CrudRepository;

public interface DisplayRepository extends CrudRepository<Display, Integer>{

	Display findDisplayByRoomId(int aDisplayId);
	
}
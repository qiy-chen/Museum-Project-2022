package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;

public interface StorageRepository extends CrudRepository<Storage, Integer>{

	Storage findStorageByRoomId(int aStorageId);
	
}
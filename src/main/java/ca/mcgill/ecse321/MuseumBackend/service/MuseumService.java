package ca.mcgill.ecse321.MuseumBackend.service;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MuseumService {

	@Autowired
	MuseumRepository museumRepo;
	
	@Transactional
	public Museum getMuseumById(int id) {
	  Museum museum = museumRepo.findMuseumByMuseumId(id);
		if (museum == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Museum not found.");
		}
		return museum;
	}
	
	@Transactional
    public Museum createmuseum(Museum museum) {
        museum = museumRepo.save(museum);
        return museum;
    }
}

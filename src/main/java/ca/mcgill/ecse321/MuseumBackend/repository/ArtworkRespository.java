package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;

public interface ArtworkRepository extends CrudRepository<Artwork, Integer>{

	Artwork findArtworkByArtworkId(int aArtworkId);
	
}
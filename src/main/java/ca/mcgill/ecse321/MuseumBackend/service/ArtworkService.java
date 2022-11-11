package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;

@Service
public class ArtworkService {
  @Autowired
  ArtworkRepository artworkRepository;
  
  @Transactional
  public Artwork getArtworkByArtworkId(int id) {
      Artwork artwork = artworkRepository.findArtworkByArtworkId(id);
      return artwork;
  }
  @Transactional
  public Artwork createArtwork(Artwork artwork) {
      artwork = artworkRepository.save(artwork);
      return artwork;
  }
}

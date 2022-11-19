package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class ArtworkRepositoryTests {
  
  @Autowired
  private ArtworkRepository artworkRepository;
  
  @Autowired
  private DisplayRepository displayRepository;

  @AfterEach
  public void clearDatabase() {
      artworkRepository.deleteAll();
      displayRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadArtwork() {
    
    
    //create display room
    Display frenchRoom = new Display();
    frenchRoom = displayRepository.save(frenchRoom);
    int roomId = frenchRoom.getRoomId();
  
    //create artwork
    Artwork monaLisa = new Artwork();
    String artName = "Mona Lisa";
    Boolean isLoanable = false;
    monaLisa.setArtworkName(artName);
    monaLisa.setIsLoanable(isLoanable);
    monaLisa.setRoom(frenchRoom);
    monaLisa = artworkRepository.save(monaLisa);
    int artworkId = monaLisa.getArtworkId();
    
    //Check find method in repo
    monaLisa = null;
    monaLisa = artworkRepository.findArtworkByArtworkId(artworkId);
    
    // Check attributes
    assertEquals(isLoanable, monaLisa.getIsLoanable());
    assertEquals(artName, monaLisa.getArtworkName());
    
    // Check Associations
    assertNotNull(monaLisa.getRoom());
    assertEquals(roomId, monaLisa.getRoom().getRoomId());
    
    
  }

}

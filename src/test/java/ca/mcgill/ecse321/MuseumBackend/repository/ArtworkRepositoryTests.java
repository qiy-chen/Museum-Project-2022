package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtworkRepositoryTests {
  
  @Autowired
  private ArtworkRepository artworkRepository;

  @Autowired
  private LoanRepository loanRepository;

  @Autowired
  private MuseumRepository museumRepository;
  
  @Autowired
  private DisplayRepository displayRepository;

  @AfterEach
  public void clearDatabase() {
      museumRepository.deleteAll();
      artworkRepository.deleteAll();
      //loanRepository.deleteAll();
      displayRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadArtwork() {
    
    //create a museum 
    Museum beauty = new Museum(1);
    //beauty.setMuseumId(0);
    beauty =  museumRepository.save(beauty);
    
    //create display room
    Display frenchRoom = new Display();
    frenchRoom.setMaxArtworks(200);
    frenchRoom.setMuseum(beauty);
    frenchRoom.setRoomNumber(3);
    frenchRoom.setRoomId(123);
    frenchRoom.setMuseum(beauty);
    frenchRoom = displayRepository.save(frenchRoom);
  
    //create artwork
    Artwork monaLisa = new Artwork();
    int artworkId = 555;
    String artName = "Mona Lisa";
    Boolean isLoanable = false;
    int valueM = 12;
    monaLisa.setArtworkName(artName);
    monaLisa.setIsLoanable(isLoanable);
    monaLisa.setMuseum(beauty);
    monaLisa.setRoom(frenchRoom);
    monaLisa.setValue(12);
    monaLisa.setArtworkId(valueM);
    monaLisa = artworkRepository.save(monaLisa);
    
    artworkId = monaLisa.getArtworkId();
 // Check if artwork is not null
    assertNotNull(monaLisa);
    
//Check find method in repo
    monaLisa = null;
    monaLisa = artworkRepository.findArtworkByArtworkId(artworkId);
    assertNotNull(monaLisa);

// Check attributes
    assertEquals(isLoanable, monaLisa.getIsLoanable());
    assertEquals(artName, monaLisa.getArtworkName());
    
    // Check Associations

    assertNotNull(monaLisa.getMuseum());
    assertEquals(1, monaLisa.getMuseum().getMuseumId());

    assertNotNull(monaLisa.getRoom());
    assertEquals(123, monaLisa.getRoom().getRoomId()); 
    
    
  }

}

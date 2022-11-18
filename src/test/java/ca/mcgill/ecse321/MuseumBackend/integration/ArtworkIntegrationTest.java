package ca.mcgill.ecse321.MuseumBackend.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArtworkIntegrationTest {
  
  @Autowired
  private TestRestTemplate client;
  
  @Autowired
  private ArtworkRepository artworkRepository;
  
  @Autowired
  private DisplayRepository displayRepository;
  
  @Autowired
  private MuseumRepository museumRepository;
  
  @Autowired
  private StorageRepository storageRepository;
  
  @BeforeEach
  @AfterEach
  public void clearDatabase() {
      museumRepository.deleteAll();
      displayRepository.deleteAll();
      storageRepository.deleteAll();
      artworkRepository.deleteAll();
  }
  
  @Test
  public void testGetAndCreateArtwork() {
    
    int id= testCreateArtwork();
    testGetArtworkById(id);
  }

  private int testCreateArtwork() {
    
    Museum m = new Museum();
    //m.setMuseumId(1);
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);

    Storage s = new Storage();
    s = storageRepository.save(s);
    
    assertTrue(d.getRoomId()>=1);
    assertTrue(m.getMuseumId()>=1);
    ResponseEntity<ArtworkResponseDto> response = client.postForEntity("/artworks", new ArtworkRequestDto("Mona Lisa", d.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    
    //ResponseEntity<String> response = client.postForEntity("/artworks", new ArtworkRequestDto("Mona Lisa", 12, 1), String.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("Mona Lisa", response.getBody().getArtworkName(), "Response has correct name");
    assertTrue(response.getBody().getArtworkId() > 0, "Response has valid ID");
    assertEquals(d.getRoomId(), response.getBody().getRoomId(), "Response has correct room ID");
    assertEquals(false, response.getBody().getIsLoanable(), "Response has correct loan availability");
    assertEquals(0, response.getBody().getValue(), "Response has correct value");
    
    return response.getBody().getArtworkId();
  }

  private void testGetArtworkById(int id) {
    
    ResponseEntity<ArtworkResponseDto> response = client.getForEntity("/artworks/" + id, ArtworkResponseDto.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("Mona Lisa", response.getBody().getArtworkName(), "Response has correct name");
    assertTrue(response.getBody().getArtworkId() == id, "Response has correct ID");
    //assertEquals(, response.getBody().getRoomId(), "Response has correct room ID");
    assertEquals(false, response.getBody().getIsLoanable(), "Response has correct loan availability");
    assertEquals(0, response.getBody().getValue(), "Response has correct value");
    
  }
}

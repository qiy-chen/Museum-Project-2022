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
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Room;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.RoomRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArtworkIntegrationTests {
  
  @Autowired
  private TestRestTemplate client;
  
  @Autowired
  private ArtworkRepository artworkRepository;
  
  @Autowired
  private RoomRepository roomRepository;
  
  @Autowired
  private DisplayRepository displayRepository;
  
  @BeforeEach
  @AfterEach
  public void clearDatabase() {
      artworkRepository.deleteAll();
  }
  
  @Test
  private void testAndCreateArtwork() {
    
    int id= testCreateArtwork();
    testGetArtworkById(id);
  }

  private int testCreateArtwork() {
    
    Room r = new Display();
    r.setRoomId(123);
    r.setRoomNumber(3);
    roomRepository.save(r);
    displayRepository.save((Display) r);
    
    ResponseEntity<ArtworkDto> response = client.postForEntity("/artworks", new ArtworkDto("Mona Lisa", 123), ArtworkDto.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("Mona Lisa", response.getBody().getName(), "Response has correct name");
    assertTrue(response.getBody().getId() > 0, "Response has valid ID");
    assertEquals(123, response.getBody().getRoomId(), "Response has correct room ID");
    assertEquals(false, response.getBody().getIsLoanable(), "Response has correct loan availability");
    assertEquals(0, response.getBody().getvalue(), "Response has correct value");
    
    return response.getBody().getId();
  }

  private void testGetArtworkById(int id) {
    
    ResponseEntity<ArtworkDto> response = client.getForEntity("/artworks/" + id, ArtworkDto.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("Mona Lisa", response.getBody().getName(), "Response has correct name");
    assertTrue(response.getBody().getId() == id, "Response has correct ID");
    assertEquals(123, response.getBody().getRoomId(), "Response has correct room ID");
    assertEquals(false, response.getBody().getIsLoanable(), "Response has correct loan availability");
    assertEquals(0, response.getBody().getvalue(), "Response has correct value");
    
  }
}
  

  class ArtworkDto {
    
    private String artworkName;
    private int roomId;
    private int artworkId;
    private double value;
    private boolean isLoanable;
    
    public ArtworkDto() {}

    public ArtworkDto(String artworkName, int roomId) {
      this.artworkName = artworkName;
      this.roomId = roomId;
      }
     

  public String getName() {
      return this.artworkName;
  }

  public int getId() {
    return this.artworkId;
  }

  public double getvalue() {
    return this.value;
  }

  public boolean getIsLoanable() {
    return this.isLoanable;
  }

  public int getRoomId() {
    return this.roomId;
  }
    
}


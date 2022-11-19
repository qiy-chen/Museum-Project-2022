package ca.mcgill.ecse321.MuseumBackend.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
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
      artworkRepository.deleteAll();
      displayRepository.deleteAll();
      storageRepository.deleteAll();
      museumRepository.deleteAll();
  }
  
  @Test
  public void testGetAndCreateArtwork() {
    
    int id= testCreateArtwork();
    testGetArtworkById(id);
  }

  private int testCreateArtwork() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);

    Storage s = new Storage();
    s = storageRepository.save(s);
    
    assertTrue(d.getRoomId()>=1);
    assertTrue(m.getMuseumId()>=1);
    ResponseEntity<ArtworkResponseDto> response = client.postForEntity("/artwork", new ArtworkRequestDto("Mona Lisa", d.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    
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
    
    ResponseEntity<ArtworkResponseDto> response = client.getForEntity("/artwork/" + id, ArtworkResponseDto.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("Mona Lisa", response.getBody().getArtworkName(), "Response has correct name");
    assertTrue(response.getBody().getArtworkId() == id, "Response has correct ID");
    assertEquals(false, response.getBody().getIsLoanable(), "Response has correct loan availability");
    assertEquals(0, response.getBody().getValue(), "Response has correct value");
    
  }
  
  @Test
  public void testGetNonExistingArtwork() {
    
    ResponseEntity<String> response = client.getForEntity("/artwork/" + 999999, String.class);
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    assertEquals(response.getBody(),"Artwork not found.", "Response has correct error message");
  }
  
  @Test
  public void testUpdateArtwork() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);
    
    Artwork art = new Artwork();
    art.setArtworkName("Mona Lisa");
    art.setIsLoanable(false);
    art.setValue(0);
    art.setMuseum(m);
    art.setRoom(d);
    art = artworkRepository.save(art);
    
    int id = art.getArtworkId();
    
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<ArtworkRequestDto> entity = new HttpEntity<ArtworkRequestDto>(new ArtworkRequestDto("La Joconde", 10, true),mHttpHeaders);
    
    ResponseEntity<ArtworkResponseDto> responseAfterUpdate = client.exchange("/artwork/"+id+"/", HttpMethod.PUT, entity, ArtworkResponseDto.class);
    
    assertNotNull(responseAfterUpdate);
    assertEquals(HttpStatus.OK, responseAfterUpdate.getStatusCode(), "Response has correct status");
    assertNotNull(responseAfterUpdate.getBody(), "Response has body");
    assertEquals("La Joconde", responseAfterUpdate.getBody().getArtworkName(), "Response has correct name");
    assertEquals(art.getArtworkId(), responseAfterUpdate.getBody().getArtworkId(), "Response has valid ID");
    assertEquals(d.getRoomId(), responseAfterUpdate.getBody().getRoomId(), "Response has correct room ID");
    assertEquals(true, responseAfterUpdate.getBody().getIsLoanable(), "Response has correct loan availability");
    assertEquals(10, responseAfterUpdate.getBody().getValue(), "Response has correct value");
        
  }
  
  //update artwork not available for loan with a price for loan 
  @Test
  public void testUpdateArtworkWithInvalidRequest() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);
    
    Artwork art = new Artwork();
    art.setArtworkName("Mona Lisa");
    art.setIsLoanable(false);
    art.setValue(0);
    art.setMuseum(m);
    art.setRoom(d);
    art = artworkRepository.save(art);
    
    int id = art.getArtworkId();
    
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<ArtworkRequestDto> entity = new HttpEntity<ArtworkRequestDto>(new ArtworkRequestDto("La Joconde", 10, false),mHttpHeaders);
    
    ResponseEntity<String> responseAfterUpdate = client.exchange("/artwork/"+id+"/", HttpMethod.PUT, entity, String.class);
    
    assertEquals(HttpStatus.BAD_REQUEST, responseAfterUpdate.getStatusCode(), "Response has correct status");
    assertEquals(responseAfterUpdate.getBody(),"Artwork is not available for loan.", "Response has correct error message");
  }
  
  //update artwork with empty string 
  @Test
  public void testUpdateArtworkWithInvalidRequest2() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);
    
    Artwork art = new Artwork();
    art.setArtworkName("Mona Lisa");
    art.setIsLoanable(false);
    art.setValue(0);
    art.setMuseum(m);
    art.setRoom(d);
    art = artworkRepository.save(art);
    
    int id = art.getArtworkId();
    
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<ArtworkRequestDto> entity = new HttpEntity<ArtworkRequestDto>(new ArtworkRequestDto("", 10, true),mHttpHeaders);
    
    ResponseEntity<String> responseAfterUpdate = client.exchange("/artwork/"+id+"/", HttpMethod.PUT, entity, String.class);
    
    assertEquals(HttpStatus.BAD_REQUEST, responseAfterUpdate.getStatusCode(), "Response has correct status");
    assertEquals(responseAfterUpdate.getBody(),"Artwork must have a name.", "Response has correct error message");
  }
  
  
  @Test
  public void testMoveArtwork() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);
    
    Storage s = new Storage();
    s.setRoomNumber(2);
    s = storageRepository.save(s);
    
    Artwork art = new Artwork();
    art.setArtworkName("Mona Lisa");
    art.setIsLoanable(false);
    art.setValue(0);
    art.setMuseum(m);
    art.setRoom(d);
    art = artworkRepository.save(art);
    
    int id = art.getArtworkId();
    
    HttpHeaders mHttpHeaders = new HttpHeaders();
    mHttpHeaders.add("Content-Type", "application/json");
    HttpEntity<ArtworkRequestDto> entity = new HttpEntity<ArtworkRequestDto>(new ArtworkRequestDto(s.getRoomId()),mHttpHeaders);
    
    ResponseEntity<ArtworkResponseDto> responseAfterUpdate = client.exchange("/artwork/room/"+id, HttpMethod.PUT, entity, ArtworkResponseDto.class);
    
    assertNotNull(responseAfterUpdate);
    assertEquals(HttpStatus.OK, responseAfterUpdate.getStatusCode(), "Response has correct status");
    assertEquals(art.getArtworkId(), responseAfterUpdate.getBody().getArtworkId(), "Response has valid ID");
    assertEquals(s.getRoomId(), responseAfterUpdate.getBody().getRoomId(), "Response has correct room ID");
    assertEquals(false, responseAfterUpdate.getBody().getIsLoanable(), "Response has correct loan availability");
    assertEquals(0, responseAfterUpdate.getBody().getValue(), "Response has correct value");
  }
  
  @Test
  public void testDeleteArtwork() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);

    Storage s = new Storage();
    s = storageRepository.save(s);
    
    assertTrue(d.getRoomId()>=1);
    assertTrue(m.getMuseumId()>=1);
    //create the artwork by using postmapping
    ResponseEntity<ArtworkResponseDto> response = client.postForEntity("/artwork", new ArtworkRequestDto("Mona Lisa", d.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    
    int id = response.getBody().getArtworkId();
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    
    ResponseEntity<ArtworkResponseDto> response2 = client.exchange("/artwork/"+id, HttpMethod.DELETE, null, ArtworkResponseDto.class);
    
    assertNotNull(response2);
    assertEquals(HttpStatus.OK, response2.getStatusCode(), "Response has correct status");
    
    //try to find the artwork by using getmapping
    ResponseEntity<String> response3 = client.getForEntity("/artwork/" + id, String.class);
    assertNotNull(response3);
    assertEquals(HttpStatus.NOT_FOUND, response3.getStatusCode(), "Response has correct status");
    assertEquals(response3.getBody(),"Artwork not found.", "Response has correct error message");
    
  }
  
  @Test
  public void testGetAllArtworks() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);

    Storage s = new Storage();
    s = storageRepository.save(s);
    
    assertTrue(d.getRoomId()>=1);
    assertTrue(m.getMuseumId()>=1);
    
    //create the artwork by using postmapping
    ResponseEntity<ArtworkResponseDto> response = client.postForEntity("/artwork", new ArtworkRequestDto("Mona Lisa", d.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    int id = response.getBody().getArtworkId();
    ResponseEntity<ArtworkResponseDto> response2 = client.postForEntity("/artwork", new ArtworkRequestDto("La Joconde", d.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    int id2 = response2.getBody().getArtworkId();
    
    ResponseEntity<List<ArtworkResponseDto>> response3 = client.exchange("/artwork",HttpMethod.GET, null, new ParameterizedTypeReference<List<ArtworkResponseDto>>() {});
    List<ArtworkResponseDto> responseList = response3.getBody();
    
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response3.getStatusCode(), "Response has correct status");
    assertNotNull(response3.getBody(), "Response has body");
    assertEquals(2, responseList.size(), "Response has correct number of tickets");
    assertEquals(id, responseList.get(0).getArtworkId(), "Response has correct ID for artwork 1");
    assertEquals(id2, responseList.get(1).getArtworkId(), "Response has correct ID for artwork 2");
  }
  
  @Test
  public void testGetAllArtworksByRoomId() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);

    Storage s = new Storage();
    s = storageRepository.save(s);
    
    assertTrue(d.getRoomId()>=1);
    assertTrue(m.getMuseumId()>=1);
    
    //create the artwork by using postmapping
    ResponseEntity<ArtworkResponseDto> response = client.postForEntity("/artwork", new ArtworkRequestDto("Mona Lisa", d.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    int id = response.getBody().getArtworkId();
    ResponseEntity<ArtworkResponseDto> response2 = client.postForEntity("/artwork", new ArtworkRequestDto("La Joconde", s.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    int id2 = response2.getBody().getArtworkId();
    

    int roomId = s.getRoomId();
    ResponseEntity<List<ArtworkResponseDto>> response3 = client.exchange("/room/artworks/"+roomId,HttpMethod.GET, null, new ParameterizedTypeReference<List<ArtworkResponseDto>>() {});
    List<ArtworkResponseDto> responseList = response3.getBody();
    
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response3.getStatusCode(), "Response has correct status");
    assertNotNull(response3.getBody(), "Response has body");
    assertEquals(1, responseList.size(), "Response has correct number of artworks");
    assertEquals(id2, responseList.get(0).getArtworkId(), "Response has correct ID for artwork 1");
  }
  
  @Test
  public void testGetAllArtworksOnDisplay() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);

    Storage s = new Storage();
    s = storageRepository.save(s);
    
    assertTrue(d.getRoomId()>=1);
    assertTrue(m.getMuseumId()>=1);
    
    //create the artwork by using postmapping
    ResponseEntity<ArtworkResponseDto> response = client.postForEntity("/artwork", new ArtworkRequestDto("Mona Lisa", d.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    int id = response.getBody().getArtworkId();
    ResponseEntity<ArtworkResponseDto> response2 = client.postForEntity("/artwork", new ArtworkRequestDto("La Joconde", s.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    
    ResponseEntity<List<ArtworkResponseDto>> response3 = client.exchange("/display/artworks",HttpMethod.GET, null, new ParameterizedTypeReference<List<ArtworkResponseDto>>() {});
    List<ArtworkResponseDto> responseList = response3.getBody();
    
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response3.getStatusCode(), "Response has correct status");
    assertNotNull(response3.getBody(), "Response has body");
    assertEquals(1, responseList.size(), "Response has correct number of artworks");
    assertEquals(id, responseList.get(0).getArtworkId(), "Response has correct ID for artwork 1");
  }
  
  @Test
  public void testGetAllArtworksOnStorage() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    Display d = new Display();
    d.setRoomNumber(3);
    d.setMaxArtworks(200);
    d = displayRepository.save(d);

    Storage s = new Storage();
    s = storageRepository.save(s);
    
    assertTrue(d.getRoomId()>=1);
    assertTrue(m.getMuseumId()>=1);
    
    //create the artwork by using postmapping
    ResponseEntity<ArtworkResponseDto> response = client.postForEntity("/artwork", new ArtworkRequestDto("Mona Lisa", d.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    ResponseEntity<ArtworkResponseDto> response2 = client.postForEntity("/artwork", new ArtworkRequestDto("La Joconde", s.getRoomId(), m.getMuseumId()), ArtworkResponseDto.class);
    int id2 = response2.getBody().getArtworkId();
    
    ResponseEntity<List<ArtworkResponseDto>> response3 = client.exchange("/storage/artworks",HttpMethod.GET, null, new ParameterizedTypeReference<List<ArtworkResponseDto>>() {});
    List<ArtworkResponseDto> responseList = response3.getBody();
    
    assertNotNull(responseList);
    assertEquals(HttpStatus.OK, response3.getStatusCode(), "Response has correct status");
    assertNotNull(response3.getBody(), "Response has body");
    assertEquals(1, responseList.size(), "Response has correct number of artworks");
    assertEquals(id2, responseList.get(0).getArtworkId(), "Response has correct ID for artwork 1");
  }
  
}

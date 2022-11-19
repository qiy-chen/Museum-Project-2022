package ca.mcgill.ecse321.MuseumBackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ca.mcgill.ecse321.MuseumBackend.dto.DisplayDto;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoomIntegrationTest {
  
  @Autowired
  private TestRestTemplate client;
  
  @Autowired
  private DisplayRepository displayRepository;
  
  @Autowired
  private MuseumRepository museumRepository;
  
  @Autowired
  private StorageRepository storageRepository;
  
  @BeforeEach
  @AfterEach
  public void clearDatabase() {
      displayRepository.deleteAll();
      storageRepository.deleteAll();
      museumRepository.deleteAll();
  }
  
  @Test
  public void testGetAndCreateDisplay() {
    
    int id= testCreateDisplay();
    testGetDisplayById(id);
  }
  
  private int testCreateDisplay() {
    
    Museum m = new Museum();
    m= museumRepository.save(m);
    
    ResponseEntity<DisplayDto> response = client.postForEntity("/display", new DisplayDto(3, 200, m.getMuseumId()), DisplayDto.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(response.getBody().getMaxArtworks(), 200);
    assertEquals(response.getBody().getNumber(), 3);
    assertEquals(response.getBody().getMuseumId(), m.getMuseumId());
    
    return response.getBody().getRoomId();   
    
  }
  
  private void testGetDisplayById(int id) {
    
    ResponseEntity<DisplayDto> response = client.getForEntity("/display/" + id, DisplayDto.class);
    
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(response.getBody().getMaxArtworks(), 200);
    assertEquals(response.getBody().getNumber(), 3);
    
  }

}

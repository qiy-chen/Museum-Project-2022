package ca.mcgill.ecse321.MuseumBackend.service;

import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtworkServiceTests {
  
  @Mock
  private ArtworkRepository artworkDao;
  @Mock
  private MuseumRepository museumDao;
  @Mock
  private DisplayRepository displayDao;
  @Mock 
  private StorageRepository storageDao;
  @Mock
  private RoomRepository roomDao;
  
  @InjectMocks
  private ArtworkService service;
  @InjectMocks
  private RoomService roomService;

  private static final int ART_ID = 123;
  private static final String name = "Mona Lisa";

  @Test
  public void testGetArtById() {
 // Tell the mocked repository how to behave
    final Artwork mona = new Artwork();
    mona.setArtworkId(ART_ID);
    mona.setArtworkName("Mona Lisa");
    
    when(artworkDao.findArtworkByArtworkId(ART_ID)).thenAnswer( (InvocationOnMock invocation) -> mona); 
      
    //test
    Artwork art = service.getArtwork(ART_ID);
    
    assertNotNull(art);
    assertEquals(name, art.getArtworkName());
    
  }
  
  @Test
  public void testCreateArtwork() {
 // Tell the mocked repository how to behave
    final String name ="Mona Lisa";
    
    final Display french = new Display();
    french.setRoomId(1);
    french.setMaxArtworks(200);
    
    final Museum m = new Museum();
    m.setMuseumId(0);
    
    final Storage str = new Storage();
    str.setRoomId(2);
    
    when(roomDao.findRoomByRoomId(1)).thenAnswer( (InvocationOnMock invocation) -> french);
    when(museumDao.findMuseumByMuseumId(0)).thenAnswer( (InvocationOnMock invocation) -> m);
    when(artworkDao.save(any(Artwork.class))).thenAnswer( (InvocationOnMock invocation) -> invocation.getArgument(0)); 
    //test
    
    ArtworkRequestDto artRequest = new ArtworkRequestDto();
    artRequest.setArtworkName(name);
    artRequest.setRoomId(1);
    artRequest.setMuseumId(0);
    
    Artwork art = service.createArtwork(artRequest);
    
    assertNotNull(art);
    assertNotNull(art.getArtworkId());
    assertEquals(name, art.getArtworkName());
    assertEquals(false, art.getIsLoanable());
    assertEquals(0, art.getValue());
    assertEquals(1, art.getRoom().getRoomId());
    
  }
  
  @Test
  public void testUpdateFields() {
    
    final Display french = new Display();
    french.setMaxArtworks(200);
    french.setRoomNumber(2);
    
    Artwork art = new Artwork();
    art.setRoom(french);
    art.setArtworkName("Mona lisa");
    art.setArtworkId(2);
    
    when(artworkDao.findArtworkByArtworkId(2)).thenAnswer( (InvocationOnMock invocation) -> art); 
    //test
    service.updateFields(art.getArtworkId(), "La Joconde", 10.0, true);
    
    assertNotNull(art);
    assertEquals("La Joconde", art.getArtworkName());
    assertEquals(true, art.getIsLoanable());
    assertEquals(10.0, art.getValue());
    
  }
  
  @Test
  public void testMoveArtwork() {
    
    final Display french = new Display();
    french.setRoomId(1);
    french.setMaxArtworks(200);
    
    final Display english = new Display();
    english.setRoomId(2);
    english.setMaxArtworks(200);
    
    final Storage arab = new Storage();
    arab.setRoomId(3);
    
    Artwork art = new Artwork();
    art.setRoom(french);
    art.setArtworkName("Mona lisa");
    
    when(artworkDao.findArtworkByArtworkId(art.getArtworkId())).thenAnswer( (InvocationOnMock invocation) -> art);
    when(roomDao.findRoomByRoomId(2)).thenAnswer( (InvocationOnMock invocation) -> english);
    
    //test
    service.moveArtwork(art.getArtworkId(), 2);
    
    assertEquals(2, art.getRoom().getRoomId());
  }
  
  @Test
  public void testDeleteArtwork() {
    
    final Display french = new Display();
    
    final Museum mus = new Museum();
    
    Artwork art = new Artwork();
    art.setArtworkId(ART_ID);
    art.setRoom(french);
    art.setArtworkName("Mona lisa");
    art.setMuseum(mus);
    
    when(artworkDao.findArtworkByArtworkId(ART_ID)).thenAnswer( (InvocationOnMock invocation) -> art);
    service.deleteArtwork(ART_ID);
    
    assertNull(art.getRoom());
    assertNull(art.getMuseum());
  }
}


package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.RoomRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

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
    
    when(displayDao.findDisplayByRoomId(1)).thenAnswer( (InvocationOnMock invocation) -> french);
    when(museumDao.findMuseumByMuseumId(0)).thenAnswer( (InvocationOnMock invocation) -> m);
    when(storageDao.findStorageByRoomId(1)).thenAnswer( (InvocationOnMock invocation) -> null);
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
    french.setRoomId(1);
    
    Artwork art = new Artwork();
    art.setArtworkId(ART_ID);
    art.setRoom(french);
    art.setArtworkName("Mona lisa");
    
    when(artworkDao.findArtworkByArtworkId(ART_ID)).thenAnswer( (InvocationOnMock invocation) -> art); 
    
    //test
    service.updateFields(art.getArtworkId(), "La Joconde", 10, true);
    
    assertNotNull(art);
    assertEquals("La Joconde", art.getArtworkName());
    assertEquals(true, art.getIsLoanable());
    assertEquals(10, art.getValue());
    assertEquals(1, art.getRoom().getRoomId());
    
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
    art.setArtworkId(ART_ID);
    art.setRoom(french);
    art.setArtworkName("Mona lisa");
    
    when(artworkDao.findArtworkByArtworkId(ART_ID)).thenAnswer( (InvocationOnMock invocation) -> art);
    when(displayDao.findDisplayByRoomId(2)).thenAnswer( (InvocationOnMock invocation) -> english);
    when(storageDao.findStorageByRoomId(2)).thenAnswer( (InvocationOnMock invocation) -> null);
    
    //test
    service.moveArtwork(ART_ID, 2);
    
    assertEquals(2, art.getRoom().getRoomId());
  }
  
  @Test
  public void testDeleteArtwork() {
    
    final Display french = new Display();
    french.setRoomId(1);
    
    Artwork art = new Artwork();
    art.setArtworkId(ART_ID);
    art.setRoom(french);
    art.setArtworkName("Mona lisa");
    
    when(artworkDao.findArtworkByArtworkId(ART_ID)).thenAnswer( (InvocationOnMock invocation) -> art);
    service.deleteArtwork(ART_ID);
    
    assertNull(art.getRoom());
  }
}


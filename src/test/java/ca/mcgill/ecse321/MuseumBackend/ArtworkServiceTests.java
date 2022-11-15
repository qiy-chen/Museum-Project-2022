package ca.mcgill.ecse321.MuseumBackend;

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
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.RoomRepository;
import ca.mcgill.ecse321.MuseumBackend.service.ArtworkService;
import ca.mcgill.ecse321.MuseumBackend.service.RoomService;

@ExtendWith(MockitoExtension.class)
public class ArtworkServiceTests {
  
  @Mock
  private ArtworkRepository artworkDao;
  @Mock
  private MuseumRepository museumDao;
  @Mock
  private DisplayRepository displayDao;
  @Mock 
  private RoomRepository roomRepository;
  
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
  
//  public void testGetArtByIdNull() {
// // Tell the mocked repository how to behave
//    final Artwork mona = null;
//    
//    when(artworkDao.findArtworkByArtworkId(ART_ID)).thenAnswer( (InvocationOnMock invocation) -> mona); 
//      
//    //test
//    Artwork art = service.getArtwork(ART_ID);
//    
//    assertNull(art);
//  }
  
  @Test
  public void testCreateArtwork() {
 // Tell the mocked repository how to behave
    final String name ="Mona Lisa";
    
    final Display french = new Display();
    french.setRoomId(1);
    
    when(roomRepository.findRoomByRoomId(1)).thenAnswer( (InvocationOnMock invocation) -> french);  
    when(artworkDao.save(any(Artwork.class))).thenAnswer( (InvocationOnMock invocation) -> invocation.getArgument(0)); 
    //test
    Artwork art = service.createArtwork(name, 1);
    
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
    
    final Display english = new Display();
    english.setRoomId(2);
    
    Artwork art = new Artwork();
    art.setArtworkId(ART_ID);
    art.setRoom(french);
    art.setArtworkName("Mona lisa");
    
    when(artworkDao.findArtworkByArtworkId(ART_ID)).thenAnswer( (InvocationOnMock invocation) -> art);
    when(roomRepository.findRoomByRoomId(2)).thenAnswer( (InvocationOnMock invocation) -> english);
    
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


package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.RoomRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {
  
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
  
  @Test
  public void testGetDisplayById() {
 // Tell the mocked repository how to behave
    final Display french = new Display();
    french.setMaxArtworks(200);
    french.setRoomNumber(2);
    
    when(displayDao.findDisplayByRoomId(french.getRoomId())).thenAnswer( (InvocationOnMock invocation) -> french); 
      
    //test
    Display disp = roomService.getDisplayById(french.getRoomId());
    
    assertNotNull(disp);
    
  }
  @Test
  public void testGetStorageById() {
 // Tell the mocked repository how to behave
    final Storage french = new Storage();
    french.setRoomNumber(2);
    
    when(storageDao.findStorageByRoomId(french.getRoomId())).thenAnswer( (InvocationOnMock invocation) -> french); 
      
    //test
    Storage str = roomService.getStorageById(french.getRoomId());
    
    assertNotNull(str);
    
  }
  
  @Test 
  public void testCreateDisplay() {
    
    final Museum mus = new Museum();
    
    when(museumDao.findMuseumByMuseumId(mus.getMuseumId())).thenAnswer( (InvocationOnMock invocation) -> mus);
    when(displayDao.save(any(Display.class))).thenAnswer( (InvocationOnMock invocation) -> invocation.getArgument(0)); 
    
    Display disp = roomService.createDisplayRoom(2, 200, mus.getMuseumId());
    
    assertNotNull(disp);
    assertEquals(2, disp.getRoomNumber());
    assertEquals(200, disp.getMaxArtworks());
    assertEquals(mus.getMuseumId(), disp.getMuseum().getMuseumId());
    }
    

}

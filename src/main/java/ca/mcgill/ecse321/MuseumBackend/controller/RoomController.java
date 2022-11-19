package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.DisplayDto;
import ca.mcgill.ecse321.MuseumBackend.dto.StorageDto;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Room;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class RoomController {

  @Autowired
  private RoomService service;

  /**
  *
  * @param name
  * @param roomNumber
  * @return maxArtworks
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/displays/", "/displays"})
  public DisplayDto createDisplay(
    @RequestParam int roomNumber,
    @RequestParam int maxArtworks
    )   throws IllegalArgumentException {

    Display d = service.createDisplayRoom(roomNumber, maxArtworks);
    return convertToDto(d);
}
  
  /**
  *
  * @param name
  * @param roomNumber
  * @throws IllegalArgumentException
  */
  @PostMapping(value = {"/storages/", "/storage"})
  public StorageDto createStorage(
    @RequestParam int roomNumber
    )   throws IllegalArgumentException {

    Storage s = service.createStorageRoom(roomNumber);
    return convertToDto(s);
}
  
  /**
  *
  * @param id
  * @return
  */
  @GetMapping(value = {"/rooms/{id}", "/rooms/{id}/"})  
  public void getRoomById(@PathVariable("id") int roomId) {
     Room r = service.getRoom(roomId);
     
     if (r instanceof Display) {getDisplay((Display)r);}
     if (r instanceof Storage) {getStorage((Storage)r);}
  }
  //helper
  public DisplayDto getDisplay(Display r) {
    return convertToDto(r);
  }
  
  //helper
  public StorageDto getStorage(Storage r) {
    return convertToDto(r);
  }

  
private DisplayDto convertToDto(Display d) {
    
    if (d == null) {
      throw new IllegalArgumentException("There is no such Display Room!");
  }
    DisplayDto displayDto = new DisplayDto(d);
    return displayDto;
}

private StorageDto convertToDto(Storage s) {
  
  if (s == null) {
    throw new IllegalArgumentException("There is no such Storage Room!");
}
  StorageDto storageDto = new StorageDto(s);
  return storageDto;
}
  //missing delete
}

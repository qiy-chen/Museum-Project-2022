package ca.mcgill.ecse321.MuseumBackend.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ca.mcgill.ecse321.MuseumBackend.exception.DisplayException;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Room;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.RoomRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

public class RoomService {
  
  @Autowired
  ArtworkRepository artworkRepository;
  
  @Autowired
  StorageRepository storageRepository;
  
  @Autowired
  DisplayRepository displayRepository;
  
  @Autowired
  RoomRepository roomRepository;
  
  @Transactional
  public Display createDisplayRoom(int aRoomNumber, int aMaxArtworks) {
    
    Display display = new Display();
    
    display.setRoomNumber(aRoomNumber);
    display.setMaxArtworks(aMaxArtworks);
    
    displayRepository.save(display);
    roomRepository.save(display);
    return display;
  }
  
  @Transactional
  public Display updateDisplayRoom(int aRoomId, int aRoomNumber, int aMaxArtworks) {
    
    Display display = displayRepository.findDisplayByRoomId(aRoomId);
    
    display.setRoomNumber(aRoomNumber);
    display.setMaxArtworks(aMaxArtworks);
   
    displayRepository.save(display);
    roomRepository.save(display);
    return display;
  }
  

  //@SuppressWarnings("unused")
  @Transactional
  public void deleteDisplayRoom(int aRoomId) {
    Display display = displayRepository.findDisplayByRoomId(aRoomId);
    
    if (display == null) {
      throw new DisplayException(HttpStatus.NOT_FOUND, "Display not found.");
    }
    
    if (display.numberOfArtworks() > 0) {
      throw new DisplayException(HttpStatus.BAD_REQUEST, "Display room is not empty");
      
    }
    
    displayRepository.delete(display);
    roomRepository.delete(display);
  }
  
  @Transactional
  public Room getRoom(int aRoomId) {
    Room room = roomRepository.findRoomByRoomId(aRoomId);
    
    if (room ==null ) {throw new DisplayException(HttpStatus.NOT_FOUND, "Room not found.");}
    
    return room; 
  }
  
  @Transactional
  public Storage createStorageRoom(int aRoomNumber) {
    
    if (storageRepository.count() >= 1 ) {throw new DisplayException(HttpStatus.BAD_REQUEST, "Already one existing storage room.");}
    
    Storage storage = new Storage();
    
    storage.setRoomNumber(aRoomNumber);
    storage.setRoomId(storage.hashCode());
    
    storageRepository.save(storage);
    roomRepository.save(storage);
    return storage;
  }

}

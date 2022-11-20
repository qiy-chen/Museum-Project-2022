package ca.mcgill.ecse321.MuseumBackend.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.MuseumBackend.Exception.DisplayException;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.RoomRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

@Service
public class RoomService {

  @Autowired
  StorageRepository storageRepository;
  
  @Autowired
  DisplayRepository displayRepository;
  
  @Autowired
  RoomRepository roomRepository;
  
  @Autowired
  MuseumRepository museumRepository;
  
  @Transactional
  public Display createDisplayRoom(int aRoomNumber, int aMaxArtworks, int museumId) {
    
    Museum mus = museumRepository.findMuseumByMuseumId(museumId);
    
    if (aRoomNumber < 1) {throw new DisplayException(HttpStatus.BAD_REQUEST, "Room number must be valid.");}
    if (aMaxArtworks < 1) {throw new DisplayException(HttpStatus.BAD_REQUEST, "Room must have a maximum number of artworks.");}
    
    Display display = new Display();
    
    display.setRoomNumber(aRoomNumber);
    display.setMaxArtworks(aMaxArtworks);
    display.setMuseum(mus);
    
    display = displayRepository.save(display);
    mus.addRoom(display);
    
    museumRepository.save(mus);
    
    return display;
  }
  
  @Transactional
  public void deleteDisplayRoom(int roomId) {
    Display display = displayRepository.findDisplayByRoomId(roomId);
    
    if (display == null) {
      throw new DisplayException(HttpStatus.NOT_FOUND, "Display not found.");
    }
    
    if (display.numberOfArtworks() >= 1) {
      throw new DisplayException(HttpStatus.BAD_REQUEST, "Display room is not empty");
    }
    
    Museum m = display.getMuseum();
    m.removeRoom(display);
    
    display.delete();
    roomRepository.delete(display);
  }
  
  @Transactional
  public Storage getStorageById(int aRoomId) {
    Storage room = storageRepository.findStorageByRoomId(aRoomId);
    
    if (room ==null ) {throw new DisplayException(HttpStatus.NOT_FOUND, "Storage room not found.");}
    
    return room; 
  }
  
  @Transactional
  public Display getDisplayById(int aRoomId) {
    Display room = displayRepository.findDisplayByRoomId(aRoomId);
    
    if (room ==null ) {throw new DisplayException(HttpStatus.NOT_FOUND, "Display room not found.");}
    
    return room; 
  }
  
  @Transactional
  public Storage createStorageRoom(int aRoomNumber, int museumId) {
    
    if (storageRepository.count() >= 1 ) {throw new DisplayException(HttpStatus.BAD_REQUEST, "Already one existing storage room.");}
    
    Museum mus = museumRepository.findMuseumByMuseumId(museumId);
    
    Storage storage = new Storage();
    
    storage.setRoomNumber(aRoomNumber);
    storage.setMuseum(mus);
    
    storage = storageRepository.save(storage);
    mus.addRoom(storage);
    
    museumRepository.save(mus);
    
    return storage;
  }

}

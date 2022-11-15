package ca.mcgill.ecse321.MuseumBackend.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.exception.ArtworkException;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Room;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;
import ca.mcgill.ecse321.MuseumBackend.repository.ArtworkRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.DisplayRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.LoanRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.RoomRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.StorageRepository;

@Service
public class ArtworkService {
  
  @Autowired
  ArtworkRepository artworkRepository;
  
  @Autowired
  StorageRepository storageRepository;
  
  @Autowired
  DisplayRepository displayRepository;
  
  @Autowired
  MuseumRepository museumRepository;
  
  @Autowired
  LoanRepository loanRepository;
  
  @Autowired
  RoomRepository roomRepository;  //make private
  
  @Transactional
  public Artwork createArtwork(String name, int roomId) { //remove museum param
    Artwork art = new Artwork();
    
    if (name == null) {
      throw new ArtworkException(HttpStatus.BAD_REQUEST, "Artwork must have a name.");
  }  
    art.setArtworkName(name);
    art.setIsLoanable(false);            //automatically set to not loanable
    art.setValue(0);                     //value is 0, not loanable   
    
    Room room = roomRepository.findRoomByRoomId(roomId);
    
    if (room instanceof Display) {           //if we're adding to a display room 
      
      if (room.numberOfArtworks() < 200) {   //if room is not full
        
        art.setRoom(room);
        room.addArtwork(art);
        displayRepository.save((Display) room);  //save the room
      }
      
    }
    if (room instanceof Storage) {
      
      art.setRoom(room);
      room.addArtwork(art);
      storageRepository.save((Storage) room);   //save the storage
    }
    
    roomRepository.save(room);
    art = artworkRepository.save(art);
    return art;
  }
  
  @Transactional
  public Room getRoom(int roomId) {
    Room room = roomRepository.findRoomByRoomId(roomId);
    //if (room==null) {throw new ArtworkException(HttpStatus.BAD_REQUEST, "Artwork is in storage");}
    return room;
  }
  
  @Transactional
  public List<Artwork> getArtworksAvailableForLoan(){
    
    List<Artwork> l = getAllArtwork();
    List<Artwork> r = new ArrayList<Artwork>();
    
    for (Artwork a : l) {
      if (a.getLoans().size()==0) {
        r.add(a);
      }
    }
    return r;
  }
  
  //update fields 
  @Transactional
  public Artwork updateFields(int artId, String name, double value, boolean isLoanable) {
    Artwork art = artworkRepository.findArtworkByArtworkId(artId);
    
    //if (name != art.getArtworkName()) {art.setArtworkName(name);}  //if the name is different 
    
    art.setArtworkName(name);
    
    if (isLoanable == true) {
      art.setIsLoanable(true);
    } else {art.setIsLoanable(false); art.setValue(0);}
    
    if (value != art.getValue()) {                                 //if the value is different 
      if ((art.getIsLoanable() == true ) || (isLoanable == true)) {
        art.setValue(value);
      } else {
        throw new ArtworkException(HttpStatus.BAD_REQUEST, "Artwork is not available for loan.");
    }
      }
    
    artworkRepository.save(art);
    return art;
  }
  
  //move art to another room
  @Transactional
  public Artwork moveArtwork(int artId, int roomId) {
    Artwork art = artworkRepository.findArtworkByArtworkId(artId);
    Room room = roomRepository.findRoomByRoomId(roomId);
    
    if (room instanceof Display) {           //if we're adding to a display room 
      if (room.numberOfArtworks() < 200) {   //if room is not full
        
        art.setRoom(room);
        room.addArtwork(art);
        displayRepository.save((Display) room);  //save the room
      }
    }
    
    if (room instanceof Storage) {
      
      art.setRoom(room);
      room.addArtwork(art);
      storageRepository.save((Storage) room);   //save the storage
    }
  
    artworkRepository.save(art);
    return art;
  }
  
  @Transactional
  public Artwork getArtwork(int artId) {
    Artwork art = artworkRepository.findArtworkByArtworkId(artId);
    
    if (art == null) {
      throw new ArtworkException(HttpStatus.NOT_FOUND, "Artwork not found.");
  }
    return art; 
  }
  
  @Transactional
  public List<Artwork> getAllArtwork() {
    return toList(artworkRepository.findAll());
  }
  
  @Transactional
  public boolean deleteArtwork(int artId) {
      Artwork art = artworkRepository.findArtworkByArtworkId(artId);
      
      if (art == null) {
        throw new ArtworkException(HttpStatus.NOT_FOUND, "Artwork not found.");
    }

      Room room = art.getRoom();
      room.removeArtwork(art);
      
      if (room instanceof Storage) {storageRepository.save( (Storage) room);}  //update rooms 
      if (room instanceof Display) {displayRepository.save( (Display) room);}
      
      art.delete();
      artworkRepository.delete(art);        //delete artwork from repo
      return true;
  }
  
  //helper method 
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
  
}

package ca.mcgill.ecse321.MuseumBackend.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ArtworkResponseDto;
import ca.mcgill.ecse321.MuseumBackend.exception.ArtworkException;
import ca.mcgill.ecse321.MuseumBackend.exception.DisplayException;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
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
  LoanRepository loanRepository;
  
  @Autowired
  MuseumRepository museumRepository;
  
  @Autowired 
  RoomRepository roomRepository;
  
  @Transactional
  public Artwork createArtwork(ArtworkRequestDto artworkRequest) { 
    
    Artwork art = new Artwork();
    
    if (artworkRequest.getArtworkName() == null) {
      throw new ArtworkException(HttpStatus.BAD_REQUEST, "Artwork must have a name.");
  }  
    art.setArtworkName(artworkRequest.getArtworkName());
    art.setIsLoanable(false);            //automatically set to not loanable
    art.setValue(0);                     //value is 0, not loanable   
    
    Museum mus = museumRepository.findMuseumByMuseumId(artworkRequest.getMuseumId());
    
    art.setMuseum(mus);
    
    Room r = roomRepository.findRoomByRoomId(artworkRequest.getRoomId());
    
    if (r == null) {throw new DisplayException(HttpStatus.NOT_FOUND, "Room not found.");}
    
    if (r.isFull()) {throw new DisplayException(HttpStatus.BAD_REQUEST, "Room is full");}
    
    art.setRoom(r);
    
    art = artworkRepository.save(art);
    r.addArtwork(art);
    mus.addArtwork(art);
    
    roomRepository.save(r);
    museumRepository.save(mus);
    
    return art;
  }
  
   
  
  @Transactional
  public List<Artwork> getArtworksNotAvailableForLoan(){
    
    List<Artwork> listOfArtworks = getAllArtwork();
    List<Artwork> r = new ArrayList<Artwork>();
    
    for (Artwork a : listOfArtworks) {
      for (Loan loan : a.getLoans()) {
        
        if (loan.getStatus()== LoanStatus.Approved) {
          r.add(a);
        }
      }
    }
    return r;
  }
  
  @Transactional
  public List<Artwork> getArtworksAvailableForLoan(){
    
    List<Artwork> listOfArtworks = getAllArtwork();
    List<Artwork> r = new ArrayList<Artwork>();
    
    for (Artwork a : listOfArtworks) {
      int count =0;
      
      for (Loan loan : a.getLoans()) {
        
        if (loan.getStatus() != LoanStatus.Approved) {count+=1;}  
        if (count== a.getLoans().size()) {r.add(a);}
        
      }
    }
    return r;
  }
  
  @Transactional
  public List<Artwork> getArtworksOnDisplay(){
    
    List<Artwork> listOfArtworks = (List<Artwork>) artworkRepository.findAll();
    List<Artwork> r = new ArrayList<Artwork>();
    
    for (Artwork a : listOfArtworks) {
     
      if (a.getRoom() instanceof Display) {
        r.add(a);
      }
    }
    return r;
  }
  
  //update fields 
  @Transactional
  public Artwork updateFields(int artId, String name, double value, boolean isLoanable) {
    
    Artwork art = artworkRepository.findArtworkByArtworkId(artId);
    art = artworkRepository.save(art);
    
    if (art == null) {
      throw new ArtworkException(HttpStatus.NOT_FOUND, "Artwork not found.");
  }

//    if (name == null) {
//      throw new ArtworkException(HttpStatus.BAD_REQUEST, "Artwork must have a name.");
//  }  
//    
//    if (name != art.getArtworkName()) {
//    art.setArtworkName(name);} 
//    
//    if (value != art.getValue()) {                                 //if the value is different 
//      if ((art.getIsLoanable() == true ) || (isLoanable == true)) {
//        art.setValue(value);
//      } else {
//        throw new ArtworkException(HttpStatus.BAD_REQUEST, "Artwork is not available for loan.");
//    }
//      }
//    
//    if (isLoanable != art.getIsLoanable()) {
//      
//      if (isLoanable != true  && isLoanable != false) {throw new ArtworkException(HttpStatus.BAD_REQUEST, "Artwork needs to be set to loanable or not loanable");}
//      
//    if (isLoanable == true) {
//      art.setIsLoanable(true);
//    } else {art.setIsLoanable(false); art.setValue(0.0);}
//    }
    
    art.setArtworkName(name);
    art.setIsLoanable(isLoanable);
    art.setValue(value);
    
    art = artworkRepository.save(art);
    
    roomRepository.save(art.getRoom());
    museumRepository.save(art.getMuseum());
 
    return art;
  }
  
  //move art to another room
  @Transactional
  public Artwork moveArtwork(int artId, int roomId) {
    Artwork art = artworkRepository.findArtworkByArtworkId(artId);
    
    Display d = null;
    Storage s = null;
    
    if (displayRepository.findDisplayByRoomId(roomId) != null) {
      d = displayRepository.findDisplayByRoomId(roomId);
    }
    
    if (storageRepository.findStorageByRoomId(roomId) != null) {
      s = storageRepository.findStorageByRoomId(roomId);
    }
    
    if (d == null && s== null ) {throw new DisplayException(HttpStatus.NOT_FOUND, "Room not found.");}
    
    if (d != null) {
      
      if (d.numberOfArtworks() >= d.getMaxArtworks()) {throw new DisplayException(HttpStatus.BAD_REQUEST, "Room is full");}
      
      art.setRoom(d);
      d.addArtwork(art);
      displayRepository.save(d);
    } 
    
    if (s != null) {
      art.setRoom(s);
      s.addArtwork(art);
      storageRepository.save(s);
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
  public Artwork deleteArtwork(int artId) {
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
      return art;
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

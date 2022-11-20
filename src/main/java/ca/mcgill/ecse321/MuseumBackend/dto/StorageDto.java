package ca.mcgill.ecse321.MuseumBackend.dto;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;

public class StorageDto {
  
  private int roomNumber;
  private int roomId;
  private int museumId;

  public StorageDto() {}
  
  public StorageDto(Storage s) {
   this.roomNumber = s.getRoomNumber();
   this.roomId = s.getRoomId();
   this.museumId = s.getMuseum().getMuseumId();
 }
  
  public StorageDto(int roomNumber, int museumId) {
    this.roomNumber = roomNumber;
    this.museumId = museumId;
  }
 
 public int getRoomNumber() {
   return roomNumber;
}
 
 public int getRoomId() {
   return roomId;
}
 
 public int getMuseumId() {
   return museumId;
  }
 
}

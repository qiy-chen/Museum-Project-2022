package ca.mcgill.ecse321.MuseumBackend.dto;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;

public class StorageDto {
  
  private int roomNumber;
  private int roomId;

  public StorageDto(Storage s) {
   this.roomNumber = s.getRoomNumber();
   this.roomId = s.getRoomId();

 }
 
 public int getNumber() {
   return roomNumber;
}
 
 public int getId() {
   return roomId;
}
 
}

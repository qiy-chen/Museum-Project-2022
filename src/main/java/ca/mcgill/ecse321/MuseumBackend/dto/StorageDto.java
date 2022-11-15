package ca.mcgill.ecse321.MuseumBackend.dto;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;

public class StorageDto {
  
  private int roomNumber;
  private int roomId;
  private MuseumDto museum;

  public StorageDto(Storage s) {
   this.roomNumber = s.getRoomNumber();
   this.roomId = s.getRoomId();
   
   this.museum = new MuseumDto(s.getMuseum());;
 }
 
 public int getNumber() {
   return roomNumber;
}
 
 public int getId() {
   return roomId;
}
 
 public MuseumDto getMuseum() {
   return museum;
 }
 
 public void setMuseum(MuseumDto mdt) {
   this.museum = mdt;
 }
 
}

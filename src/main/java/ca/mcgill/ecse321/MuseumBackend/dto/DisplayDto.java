package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Display;

public class DisplayDto {
  
  private int roomNumber;
  private int roomId;
  private int maxArtworks;

  public DisplayDto () {}
  
  public DisplayDto(Display d) {
   this.roomNumber = d.getRoomNumber();
   this.roomId = d.getRoomId();
   this.maxArtworks = d.getMaxArtworks();
 }
 
 public int getNumber() {
   return roomNumber;
}
 
 public int getMaxArtworks() {
   return maxArtworks;
}
 
 public int getId() {
   return roomId;
}
 
}

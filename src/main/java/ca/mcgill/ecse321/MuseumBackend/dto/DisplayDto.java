package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Display;

public class DisplayDto {
  
  private int roomNumber;
  private int roomId;
  private int maxArtworks;
  private int museumId;

  public DisplayDto () {}
  
  public DisplayDto(Display d) {
   this.roomNumber = d.getRoomNumber();
   this.roomId = d.getRoomId();
   this.maxArtworks = d.getMaxArtworks();
   this.museumId = d.getMuseum().getMuseumId();
 }
  
  public DisplayDto(int roomNumber, int maxArtworks, int museumId) {
    this.roomNumber = roomNumber;
    this.maxArtworks = maxArtworks;
    this.museumId = museumId;
  }
 
 public int getRoomNumber() {
   return roomNumber;
}
 
 public int getMaxArtworks() {
   return maxArtworks;
}
 
 public int getRoomId() {
   return roomId;
}
 
 public int getMuseumId() {
   return museumId;
 }
 
}

package ca.mcgill.ecse321.MuseumBackend.dto;

public class ArtworkRequestDto {
  
  private int roomId;

  private String artworkName;
  
  private int museumId;
  
  private int value;

  private boolean isLoanable;
  
  public ArtworkRequestDto() {}
  
  public ArtworkRequestDto(String name, int roomId, int museumId) {
    this.artworkName = name;
    this.roomId = roomId;
    this.museumId = museumId;
  }
  
  public ArtworkRequestDto(String name, int value, boolean isLoanable) {
    this.artworkName = name;
    this.value = value;
    this.isLoanable = isLoanable;
  }
  
  public ArtworkRequestDto(int roomId) {
    this.roomId = roomId;
  }
  
  public int getRoomId() {
    return this.roomId;
}
  
  public int getValue() {
    return this.value;
  }
  
  public boolean getIsLoanable() {
    return this.isLoanable;
  } 

public void setRoomId(int roomId) {
    this.roomId = roomId;
}

public String getArtworkName() {
  return this.artworkName;
}

public void setArtworkName(String artworkName) {
  this.artworkName = artworkName;
}

public void setMuseumId(int museumId) {
  this.museumId = museumId;
}

public int getMuseumId() {
  return this.museumId;
}

}

package ca.mcgill.ecse321.MuseumBackend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ArtworkRequestDto {
  
  @Min(1)
  private int roomId;
  
  @NotNull
  @NotBlank
  private String artworkName;
  
  @NotNull
  @Min(1)
  private int museumId;
  
  public ArtworkRequestDto() {}
  
  public ArtworkRequestDto(String name, int roomId, int museumId) {
    this.artworkName = name;
    this.roomId = roomId;
    this.museumId = museumId;
  }
  
  public int getRoomId() {
    return this.roomId;
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

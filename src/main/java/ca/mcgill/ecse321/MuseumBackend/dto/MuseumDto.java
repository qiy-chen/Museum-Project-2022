package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;

public class MuseumDto {
  
  private int museumId;
  
  public MuseumDto(Museum mus) {
    this.museumId = mus.getMuseumId();
  }
  
//  public MuseumDto(int museumId) {
//    this.museumId = museumId;
//  }

  public int getmuseumId() {
    return museumId;
  }
  
  
}


package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;

import java.util.List;

public class ArtworkResponseDto {
  
  private String artworkName;
  private int roomId;
  private int artworkId;
  private double value;
  private boolean isLoanable;
  private List<LoanDto> loans;
  private int museumId;

  public ArtworkResponseDto(){} 
  
  public ArtworkResponseDto(Artwork art) {
    this.artworkName = art.getArtworkName();
    this.artworkId = art.getArtworkId();
    this.value = art.getValue();
    this.isLoanable= art.getIsLoanable();
    
    this.roomId = art.getRoom().getRoomId();
    
    this.museumId = art.getMuseum().getMuseumId();
    
    for (Loan l : art.getLoans()) {
      LoanDto lDto = new LoanDto(l);
      this.loans.add(lDto);
    }
  } 
  
public List<LoanDto> getLoans() {
  return loans;
}  

public String getArtworkName() {
    return artworkName;
}

public int getArtworkId() {
  return artworkId;
}

public double getValue() {
  return value;
}

public boolean getIsLoanable() {
  return isLoanable;
}

public int getRoomId() {
  return this.roomId;
}

public int getMuseumId() {
  return this.museumId;
}
}

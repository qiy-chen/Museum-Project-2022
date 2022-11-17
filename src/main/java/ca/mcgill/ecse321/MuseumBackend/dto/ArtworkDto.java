package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;

public class ArtworkDto {
  
  private String artworkName;
  private int roomId;
  private int artworkId;
  private double value;
  private boolean isLoanable;
  private List<LoanDto> loans;

  public ArtworkDto(Artwork art) {
    this.artworkName = art.getArtworkName();
    this.artworkId = art.getArtworkId();
    this.value = art.getValue();
    this.isLoanable= art.getIsLoanable();
    
    this.roomId = art.getRoom().getRoomId();
    
    for (Loan l : art.getLoans()) {
      LoanDto lDto = new LoanDto(l);
      this.loans.add(lDto);
    }
  } 
  
public List<LoanDto> getLoans() {
  return loans;
}  

public String getName() {
    return artworkName;
}

public int getId() {
  return artworkId;
}

public double getvalue() {
  return value;
}

public boolean getIsLoanable() {
  return isLoanable;
}

public int getRoomId() {
  return this.roomId;
}
}

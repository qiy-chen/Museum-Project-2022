package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Display;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Storage;

public class ArtworkDto {
  
  private String artworkName;
  private DisplayDto disD;
  private StorageDto stD;
  private int artworkId;
  private double value;
  private boolean isLoanable;
  private List<LoanDto> loans;

  public ArtworkDto(Artwork art) {
    this.artworkName = art.getArtworkName();
    this.artworkId = art.getArtworkId();
    this.value = art.getValue();
    this.isLoanable= art.getIsLoanable();
    
    if (art.getRoom() instanceof Display) {this.disD = new DisplayDto( (Display) art.getRoom());}
    if (art.getRoom() instanceof Storage) {this.stD = new StorageDto( (Storage) art.getRoom());}
    
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

public DisplayDto getDisplayroom() {
  return disD;
}

public StorageDto getStorageroom() {
  return stD;
}
  
}

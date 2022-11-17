package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Room;

public class ArtworkDto {
  private int id;
  private String name;
  private double value;
  private boolean isLoanable;
  private List<Loan> loans;
  private Room room;
  
  public ArtworkDto(Artwork artwork) {
    super();
    this.id = artwork.getArtworkId();
    this.name = artwork.getArtworkName();
    this.value = artwork.getValue();
    this.isLoanable = artwork.getIsLoanable();
    this.loans = artwork.getLoans();
    this.room = artwork.getRoom();
  }
}

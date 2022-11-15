package ca.mcgill.ecse321.MuseumBackend.dto;

public class ArtworkDto {
  private int artworkId;
  private String artworkName;
  private double value;
  private boolean isLoanable;
  
  public ArtworkDto() {}
  
  public ArtworkDto(int aArtworkId, String aArtworkName, double aValue, boolean aIsLoanable) {
    this.artworkId = aArtworkId;
    this.artworkName = aArtworkName;
    this.value = aValue;
    this.isLoanable = aIsLoanable;
  }
  public ArtworkDto(Loan loan) {
    
  }
  public int getArtworkId() {
    return artworkId;
  }
  public String getArtworkName() {
    return artworkName;
  }
  public double getValue() {
    return value;
  }
  public boolean getIsLoanable() {
    return isLoanable;
  }
}

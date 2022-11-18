package ca.mcgill.ecse321.MuseumBackend.dto;

import javax.validation.constraints.NotNull;

public class LoanRequestDto {

  @NotNull
  private int loanNumber;
  private int museumId;
  private int customerId;
  private int numOfDays;
  private int artworkId;
  private double rentalFee;
  
public LoanRequestDto() {}
  
  public LoanRequestDto(int artworkid, int customerid, int nod,  double rf, int museumid) {
    this.artworkId = artworkid;
    this.customerId = customerid;
    this.numOfDays = nod;
    this.rentalFee = rf;
    this.loanNumber=2;
    this.museumId=museumid;
  }
  
  
  public int getLoanNumber() {
    return loanNumber;
  }

  public void setLoanNumber(int loanNumber) {
    this.loanNumber = loanNumber;
  }

  public int getMuseumId() {
    return museumId;
  }

  public void setMuseumId(int museumId) {
    this.museumId = museumId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getNumOfDays() {
    return numOfDays;
  }

  public void setNumOfDays(int numOfDays) {
    this.numOfDays = numOfDays;
  }

  public int getArtworkId() {
    return artworkId;
  }

  public void setArtworkId(int artworkId) {
    this.artworkId = artworkId;
  }

  public double getRentalFee() {
    return rentalFee;
  }

  public void setRentalFee(double rentalFee) {
    this.rentalFee = rentalFee;
  }

}

package ca.mcgill.ecse321.MuseumBackend.dto;


public class LoanRequestDto {
  private int LoanStatusAsNumber;
  private int numOfDays;
  private String startDate;
  private String endDate;
  private double rentalFee;
  private int artworkId;
  private int customerId;
  private int museumId;
  //private String dateAsString;
  
  public LoanRequestDto() {}
  
  public LoanRequestDto(int LoanStatusAsNumber, String startDate, String endDate,int numOfDays, double rentalFee, int artworkId, int customerId, int museumId ) {
    this.LoanStatusAsNumber = LoanStatusAsNumber;
    this.startDate = startDate;
    this.endDate = endDate;
    this.numOfDays = numOfDays;
    this.rentalFee = rentalFee;
    this.artworkId = artworkId;
    this.customerId = customerId;
    this.museumId = museumId;
  }
/*
  public String getDateAsString() {
    return dateAsString;
  }

  public void setDateAsString(String dateAsString) {
    this.dateAsString = dateAsString;
  }
*/

  public int getLoanStatusAsNumber() {
    return LoanStatusAsNumber;
  }

  public void setLoanStatusAsNumber(int loanStatusAsNumber) {
    LoanStatusAsNumber = loanStatusAsNumber;
  }

  public int getNumOfDays() {
    return numOfDays;
  }

  public void setNumOfDays(int numOfDays) {
    this.numOfDays = numOfDays;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public double getRentalFee() {
    return rentalFee;
  }

  public void setRentalFee(double rentalFee) {
    this.rentalFee = rentalFee;
  }

  public int getArtworkId() {
    return artworkId;
  }

  public void setArtworkId(int artworkId) {
    this.artworkId = artworkId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getMuseumId() {
    return museumId;
  }

  public void setMuseumId(int museumId) {
    this.museumId = museumId;
  }
  
}

package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;

public class LoanRequestDto {
  private int loanId;
  private int LoanStatusAsNumber;
  private int numOfDays;
  private String startDate;
  private Date endDate;
  private double rentalFee;
  private int artworkId;
  private int customerId;
  private int museumId;
  private String dateAsString;
  
  public LoanRequestDto() {}
  
  public LoanRequestDto(int LoanStatusAsNumber, String dateAsString, int numOfDays, double rentalFee, int artworkId, int customerId, int museumId ) {
    this.LoanStatusAsNumber = LoanStatusAsNumber;
    this.dateAsString = dateAsString;
    this.numOfDays = numOfDays;
    this.rentalFee = rentalFee;
    this.artworkId = artworkId;
    this.customerId = customerId;
    this.museumId = museumId;
  }

  public String getDateAsString() {
    return dateAsString;
  }

  public void setDateAsString(String dateAsString) {
    this.dateAsString = dateAsString;
  }

  public int getLoanId() {
    return loanId;
  }

  public void setLoanId(int loanId) {
    this.loanId = loanId;
  }

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

  /*public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }*/

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
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

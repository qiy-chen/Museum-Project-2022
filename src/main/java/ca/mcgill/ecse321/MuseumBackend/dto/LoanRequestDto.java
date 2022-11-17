package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import javax.validation.constraints.NotNull;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;

public class LoanRequestDto {
  //private LoanService loanService;
  @NotNull
  private int loanNumber;
  private int museumId;
  
  private LoanStatus loanStatus;
  public LoanRequestDto() {}
  
  public LoanRequestDto(int artworkid, int customerid, int nod,  double rf, int museumid) {
    this.artworkId = artworkid;
    this.customerId = customerid;
    this.numOfDays = nod;
    this.rentalFee = rf;
    this.loanNumber=2;
    this.museumId=museumid;
    //this.loanStatus = LoanStatus.Requested;
  }

  public LoanStatus getLoanStatus() {
    return loanStatus;
  }

  public void setLoanStatus(LoanStatus loanStatus) {
    this.loanStatus = loanStatus;
  }
  
  private int numOfDays;

  public int getNumOfDays() {
    return numOfDays;
  }

  public void setNumOfDays(int numOfDays) {
    this.numOfDays = numOfDays;
  }
  
  private Date startDate;

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  private Date endDate;

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  private double rentalFee;

  public double getRentalFee() {
    return rentalFee;
  }

  public void setRentalFee(double rentalFee) {
    this.rentalFee = rentalFee;
  }
  
  private int customerId;

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }
  
  private int artworkId;

  public int getArtworkId() {
    return artworkId;
  }

  public void setArtworkId(int artworkId) {
    this.artworkId = artworkId;
  }
  
  public int getStatus() {
    return this.loanNumber;
  }
  
  public void setStatus(int loan) {
    this.loanNumber=loan;
    
  }
  public int getMuseumId() {
    return this.museumId;
  }
  public void setMuseumId(int museumid) {
    this.museumId = museumid;
  }
  
  
}

package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;

public class LoanResponseDto {
  
  private int id;
  private int customerId;
  private int artworkId;
  private int museumId;
  //private LoanStatus status;
  private int statusNumber;
  private int numOfDays;
  private Date startDate;
  private Date endDate;
  private double rentalFee;
  public LoanResponseDto() {}
  
  public LoanResponseDto(Loan loan) {
    this.id = loan.getLoanId();
    this.customerId = loan.getCustomer().getPersonRoleId();
    
    this.artworkId = loan.getArtwork().getArtworkId();
    this.museumId = loan.getMuseum().getMuseumId();
    
    if (loan.getStatus()==LoanStatus.Approved) {
      this.statusNumber =0;
    }
    if (loan.getStatus()==LoanStatus.Denied) {
      this.statusNumber =1;
    }
    if (loan.getStatus()==LoanStatus.Requested) {
      this.statusNumber =2;
    }
    if (loan.getStatus()==LoanStatus.Returned) {
      this.statusNumber =3;
    }
    this.numOfDays = loan.getNumOfDays();
    this.startDate = loan.getStartDate();
    this.endDate = loan.getEndDate();
    this.rentalFee = loan.getRentalFee();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(int id) {
    this.customerId = id;
  }

  public int getArtworkId() {
    return this.artworkId;
  }

  public void setArtworkId(int id) {
    this.artworkId = id;
  }

  public int getStatus() {
    return statusNumber;
  }

  public void setStatus(int status) {
    this.statusNumber = status;
  }

  public int getNumOfDays() {
    return numOfDays;
  }

  public void setNumOfDays(int numOfDays) {
    this.numOfDays = numOfDays;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

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
  public int getMuseumId() {
    return this.museumId;
  }
  
}

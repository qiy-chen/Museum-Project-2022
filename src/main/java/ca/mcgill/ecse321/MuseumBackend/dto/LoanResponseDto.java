package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;

public class LoanResponseDto {
  
  private int id;
  private int customerId;
  private int artworkId;
  private int museumId;
  private int statusNumber;
  private int numOfDays;
  private Date startDate;
  private Date endDate;
  private double rentalFee;
  
  public LoanResponseDto() {}
  
  public LoanResponseDto(Loan loan) {
    this.id = loan.getLoanId();
    this.artworkId = loan.getArtwork().getArtworkId();
    this.customerId = (int) loan.getCustomer().getPersonRoleId();
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

  public int getCustomerId() {
    return customerId;
  }

  public int getArtworkId() {
    return artworkId;
  }

  public int getMuseumId() {
    return museumId;
  }

  public int getStatusNumber() {
    return statusNumber;
  }

  public int getNumOfDays() {
    return numOfDays;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public double getRentalFee() {
    return rentalFee;
  }

  
  
}

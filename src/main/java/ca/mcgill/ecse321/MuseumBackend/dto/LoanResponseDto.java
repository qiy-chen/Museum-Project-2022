package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;

public class LoanResponseDto {
  private int loanId;
  //private int LoanStatusAsNumber;
  private LoanStatus status;
  private int numOfDays;
  private Date startDate;
  private Date endDate;
  private double rentalFee;
  private int artworkId;
  private int customerId;
  private int museumId;
  private String dateAsString;
  
  public LoanResponseDto() {}
  
  public LoanResponseDto(Loan loan) {
    this.loanId = loan.getLoanId();
    this.numOfDays = loan.getNumOfDays();
    this.startDate = loan.getStartDate();
    this.endDate = loan.getEndDate();
    this.endDate = loan.getEndDate();
    this.rentalFee = loan.getRentalFee();
    this.status = loan.getStatus();
    System.out.println("BYE BYE HOE" +loan.getArtwork());
    this.artworkId = loan.getArtwork().getArtworkId();
    //this.customerId = loan.getCustomer().getPersonRoleId();
  }

  public int getLoanId() {
    return loanId;
  }

 public LoanStatus getStatus() {
   return this.status;
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

  public int getArtworkId() {
    return artworkId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public int getMuseumId() {
    return museumId;
  }
  public String getDateAsString() {
    return dateAsString;
  }
  
}

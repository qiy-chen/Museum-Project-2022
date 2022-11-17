package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.service.LoanService;

public class LoanRequestDto {
  //private LoanService loanService;
  
  private LoanStatus loanStatus;

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
  
  
}

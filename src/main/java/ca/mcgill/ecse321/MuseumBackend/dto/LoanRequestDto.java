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
  
  private int loanId;
  private LoanStatus status;
  private int numOfDays;
  private Date startDate;
  private Date endDate;
  private double rentalFee;
  private int artworkid;
  private int customerid;
  
  
  public int getLoanId() {
    return loanId;
  }


  public void setLoanId(int loanId) {
    this.loanId = loanId;
  }


  public LoanStatus getStatus() {
    return status;
  }


  public void setStatus(LoanStatus status) {
    this.status = status;
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


  public int getArtworkid() {
    return artworkid;
  }


  public void setArtworkid(int artworkid) {
    this.artworkid = artworkid;
  }


  public int getCustomerid() {
    return customerid;
  }


  public void setCustomerid(int customerid) {
    this.customerid = customerid;
  }
  
}

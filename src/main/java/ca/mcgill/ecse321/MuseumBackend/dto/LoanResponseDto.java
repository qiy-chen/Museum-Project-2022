package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;

public class LoanResponseDto {
  
  private ArtworkDto artwork;
  private CustomerDto customer;
  
  private int loanId;
  private LoanStatus status;
  private int numOfDays;
  private Date startDate;
  private Date endDate;
  private double rentalFee;
  
  public LoanResponseDto(Loan loan) {
    this.loanId = loan.getLoanId();
    this.status = loan.getStatus();
    this.numOfDays = loan.getNumOfDays();
    this.startDate = loan.getStartDate();
    this.endDate = loan.getEndDate();
    this.rentalFee = loan.getRentalFee();
  }
  
  public int getLoanId() {
    return loanId;
  }
  
  public void setLoanId(int id) {
    loanId = id;
  }
  
  public LoanStatus getLoanStatus() {
    return status;
  }
  
  public void setLoanStatus(LoanStatus stat) {
    status = stat;
  }
  
  public int getNumofDays() {
    return numOfDays;
  }
  
  public void setNumOfDays(int nOfDays) {
    numOfDays = nOfDays;
  }
  
  public Date getStartDate() {
    return startDate;
  }
  
  public void setStartDate(Date date) {
    startDate = date;
  }
  
  public Date getEndDate() {
    return endDate;
  }
  
  public void setEndDate(Date date) {
    endDate = date;
  }
  
  public double getRentalFee() {
    return rentalFee;
  }
  
  public void setRentalFee(double fee) {
    rentalFee = fee;
  }
  
  public ArtworkDto getArtwork(){
    return artwork;
  }
  
  public void setArtwork(ArtworkDto artwork) {
    this.artwork = artwork;
  }
  
  public CustomerDto getCustomer(){
    return customer;
  }
  
  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }
  
}

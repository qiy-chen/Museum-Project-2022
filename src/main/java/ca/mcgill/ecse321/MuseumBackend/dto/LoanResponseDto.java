package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;

public class LoanResponseDto {
  
  private int id;
  private CustomerDto customer;
  private ArtworkDto artwork;
  private LoanStatus status;
  private int numOfDays;
  private Date startDate;
  private Date endDate;
  private double rentalFee;
  
  public LoanResponseDto(Loan loan) {
    this.id = loan.getLoanId();
    this.customer = new CustomerDto(loan.getCustomer());
    this.artwork = new ArtworkDto(loan.getArtwork());
    this.status = loan.getStatus();
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

  public CustomerDto getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

  public ArtworkDto getArtwork() {
    return artwork;
  }

  public void setArtwork(ArtworkDto artwork) {
    this.artwork = artwork;
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
  
}

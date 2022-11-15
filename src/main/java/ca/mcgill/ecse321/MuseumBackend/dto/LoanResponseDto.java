package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import ca.mcgill.ecse321.MuseumBackend.model.Artwork;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Loan.LoanStatus;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;

public class LoanResponseDto {
  
  private ArtworkDto artwork;
  private CustomerDto customer;
  
  private int loanId;
  private LoanStatus status;
  private int numOfDays;
  private Date startDate;
  private Date endDate;
  private double rentalFee;
  private Museum museum;
  private Artwork artwork2;
  private Customer customer2;
  
  public LoanResponseDto(Loan loan) {
    this.loanId = loan.getLoanId();
    this.status = loan.getStatus();
    this.numOfDays = loan.getNumOfDays();
    this.startDate = loan.getStartDate();
    this.endDate = loan.getEndDate();
    this.rentalFee = loan.getRentalFee();
    this.artwork2 = loan.getArtwork();
    this.museum = loan.getMuseum();
    this.customer2 = loan.getCustomer();
    this.artwork = new ArtworkDto();
    this.customer = new CustomerDto();
  }
  public Museum getMuseum() {
    return museum;
  }
  public void setMuseum(Museum museum) {
    this.museum = museum;
  }
  public Artwork getArtwork() {
    return artwork2;
  }
  public void setArtwork(Artwork artwork2) {
    this.artwork2 = artwork2;
  }
  public Customer getCustomer() {
    return customer2;
  }
  public void setCustomer(Customer customer2) {
    this.customer2 = customer2;
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
  
  public ArtworkDto getArtworkDto(){
    return artwork;
  }
  
  public void setArtworkDto(ArtworkDto artwork) {
    this.artwork = artwork;
  }
  
  public CustomerDto getCustomerDto(){
    return customer;
  }
  
  public void setCustomerDto(CustomerDto customer) {
    this.customer = customer;
  }
  
}

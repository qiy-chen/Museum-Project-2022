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
  @Autowired
  private LoanService loanService;
  
  private int loanId;
  private LoanStatus status;
  private int numOfDays;
  private Date startDate;
  private Date endDate;
  private double rentalFee;
  private Artwork artwork;
  private Customer customer;
  private Museum museum;
  
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
  
  public Artwork getArtwork() {
    return artwork;
  }
  public void setArtwork(Artwork artwork) {
    this.artwork = artwork;
  }
  public Customer getCustomer() {
    return customer;
  }
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
  public Museum getMuseum() {
    return museum;
  }
  public void setMuseum(Museum museum) {
    this.museum = museum;
  }
  public Loan toModel() {
   Loan loan = new Loan();
   loan.setArtwork(artwork);
   loan.setCustomer(customer);
   loan.setEndDate(endDate);
   loan.setLoanId(loanId);
   loan.setMuseum(museum);
   loan.setNumOfDays(numOfDays);
   loan.setRentalFee(rentalFee);
   loan.setStartDate(startDate);
   loan.setStatus(status);
   return loan;
  }
  
}

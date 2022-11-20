package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Loan;

import java.sql.Date;

public class LoanDto {

  public String status;
  @SuppressWarnings("unused")
  private double rentalFee;
  @SuppressWarnings("unused")
  private Date startDate;
  @SuppressWarnings("unused")
  private Date endDate;
  @SuppressWarnings("unused")
  private int numOfDays;
  @SuppressWarnings("unused")
  private int loanId;
  
  public LoanDto(Loan loan) {
      super();
      this.status = loan.getStatus().toString();
      this.rentalFee = loan.getRentalFee();
      this.startDate = loan.getStartDate();
      this.endDate = loan.getEndDate();
      this.numOfDays = loan.getNumOfDays();
      this.loanId = loan.getLoanId();
  }
  
}

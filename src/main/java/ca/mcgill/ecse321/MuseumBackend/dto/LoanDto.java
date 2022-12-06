package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Loan;

import java.sql.Date;
import java.time.LocalDateTime;

public class LoanDto {

  public String status;
  private double rentalFee;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private int numOfDays;
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

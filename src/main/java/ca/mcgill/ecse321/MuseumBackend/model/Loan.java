/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

// line 42 "../../../../../Museum.ump"
@Entity
public class Loan
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LoanStatus { Requested, Approved, Denied, Returned }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Attributes
  private double rentalFee;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private int numOfDays;
  private LoanStatus status;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int loanId;

  //Loan Associations
  @ManyToOne(cascade = CascadeType.MERGE)
  private Museum museum;
  @ManyToOne(cascade = CascadeType.MERGE)
  private Customer customer;
  @ManyToOne(cascade = CascadeType.MERGE)
  private Artwork artwork;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(double aRentalFee, LocalDateTime aStartDate, LocalDateTime aEndDate, int aNumOfDays, LoanStatus aStatus, int aLoanId, Museum aMuseum, Customer aCustomer, Artwork aArtwork)
  {
    rentalFee = aRentalFee;
    startDate = aStartDate;
    endDate = aEndDate;
    numOfDays = aNumOfDays;
    status = aStatus;
    loanId = aLoanId;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create loan due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create loan due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddArtwork = setArtwork(aArtwork);
    if (!didAddArtwork)
    {
      throw new RuntimeException("Unable to create loan due to artwork. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  
  public Loan() {
    this.status = LoanStatus.Requested;
  }

  //------------------------
  // INTERFACE
  //------------------------

	// TODO Auto-generated constructor stub


public boolean setRentalFee(double aRentalFee)
  {
    boolean wasSet = false;
    rentalFee = aRentalFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDate(LocalDateTime aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(LocalDateTime aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumOfDays(int aNumOfDays)
  {
    boolean wasSet = false;
    numOfDays = aNumOfDays;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(LoanStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoanId(int aLoanId)
  {
    boolean wasSet = false;
    loanId = aLoanId;
    wasSet = true;
    return wasSet;
  }

  public double getRentalFee()
  {
    return rentalFee;
  }

  public LocalDateTime getStartDate()
  {
    return startDate;
  }

  public LocalDateTime getEndDate()
  {
    return endDate;
  }

  public int getNumOfDays()
  {
    return numOfDays;
  }

  public LoanStatus getStatus()
  {
    return status;
  }


  public int getLoanId()
  {
    return loanId;
  }
  /* Code from template association_GetOne */
  public Museum getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public Artwork getArtwork()
  {
    return artwork;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMuseum(Museum aMuseum)
  {
    boolean wasSet = false;
    if (aMuseum == null)
    {
      return wasSet;
    }

    Museum existingMuseum = museum;
    museum = aMuseum;
    if (existingMuseum != null && !existingMuseum.equals(aMuseum))
    {
      existingMuseum.removeLoan(this);
    }
    museum.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeLoan(this);
    }
    customer.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setArtwork(Artwork aArtwork)
  {
    boolean wasSet = false;
    if (aArtwork == null)
    {
      return wasSet;
    }

    Artwork existingArtwork = artwork;
    artwork = aArtwork;
    if (existingArtwork != null && !existingArtwork.equals(aArtwork))
    {
      existingArtwork.removeLoan(this);
    }
    artwork.addLoan(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Museum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeLoan(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeLoan(this);
    }
    Artwork placeholderArtwork = artwork;
    this.artwork = null;
    if(placeholderArtwork != null)
    {
      placeholderArtwork.removeLoan(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "rentalFee" + ":" + getRentalFee()+ "," +
            "numOfDays" + ":" + getNumOfDays()+ "," +
            "loanId" + ":" + getLoanId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "artwork = "+(getArtwork()!=null?Integer.toHexString(System.identityHashCode(getArtwork())):"null");
  }
}
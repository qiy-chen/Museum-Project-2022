/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.sql.Date;
import java.util.*;

// line 39 "../../../../../Museum.ump"
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
  private Date startDate;
  private Date endDate;
  private int numOfDays;
  private LoanStatus status;
  private UUID loanId;

  //Loan Associations
  private Museum museum;
  private Customer customer;
  private Artifact artifact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(double aRentalFee, Date aStartDate, Date aEndDate, int aNumOfDays, LoanStatus aStatus, UUID aLoanId, Museum aMuseum, Customer aCustomer, Artifact aArtifact)
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
    boolean didAddArtifact = setArtifact(aArtifact);
    if (!didAddArtifact)
    {
      throw new RuntimeException("Unable to create loan due to artifact. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Loan() {
	// TODO Auto-generated constructor stub
}

public boolean setRentalFee(double aRentalFee)
  {
    boolean wasSet = false;
    rentalFee = aRentalFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
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

  public boolean setLoanId(UUID aLoanId)
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

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
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

  public UUID getLoanId()
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
  public Artifact getArtifact()
  {
    return artifact;
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
  public boolean setArtifact(Artifact aArtifact)
  {
    boolean wasSet = false;
    if (aArtifact == null)
    {
      return wasSet;
    }

    Artifact existingArtifact = artifact;
    artifact = aArtifact;
    if (existingArtifact != null && !existingArtifact.equals(aArtifact))
    {
      existingArtifact.removeLoan(this);
    }
    artifact.addLoan(this);
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
    Artifact placeholderArtifact = artifact;
    this.artifact = null;
    if(placeholderArtifact != null)
    {
      placeholderArtifact.removeLoan(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "rentalFee" + ":" + getRentalFee()+ "," +
            "numOfDays" + ":" + getNumOfDays()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "loanId" + "=" + (getLoanId() != null ? !getLoanId().equals(this)  ? getLoanId().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "artifact = "+(getArtifact()!=null?Integer.toHexString(System.identityHashCode(getArtifact())):"null");
  }
}
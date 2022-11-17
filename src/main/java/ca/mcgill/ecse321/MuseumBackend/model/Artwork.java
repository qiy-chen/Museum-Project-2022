/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;

import javax.persistence.*;


import java.sql.Date;

// line 76 "../../../../../Museum.ump"
@Entity
public class Artwork
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artwork Attributes
  private boolean isLoanable;
  private double value;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int artworkId;
  private String artworkName;

  //Artwork Associations
  @ManyToOne(cascade = CascadeType.MERGE)
  private Museum museum;
  @ManyToOne(cascade = CascadeType.MERGE)
  private Room room;
  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Loan> loans;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artwork(boolean aIsLoanable, double aValue, int aArtworkId, String aArtworkName, Museum aMuseum, Room aRoom)
  {
    isLoanable = aIsLoanable;
    value = aValue;
    artworkId = aArtworkId;
    artworkName = aArtworkName;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create artwork due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddRoom = setRoom(aRoom);
    if (!didAddRoom)
    {
      throw new RuntimeException("Unable to create artwork due to room. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    loans = new ArrayList<Loan>();
  }

  public Artwork() {
    
	  loans = new ArrayList<Loan>();
  }
  //------------------------
  // INTERFACE
  //------------------------


	// TODO Auto-generated constructor stub


public boolean setIsLoanable(boolean aIsLoanable)
  {
    boolean wasSet = false;
    isLoanable = aIsLoanable;
    wasSet = true;
    return wasSet;
  }

  public boolean setValue(double aValue)
  {
    boolean wasSet = false;
    value = aValue;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtworkId(int aArtworkId)
  {
    boolean wasSet = false;
    artworkId = aArtworkId;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtworkName(String aArtworkName)
  {
    boolean wasSet = false;
    artworkName = aArtworkName;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsLoanable()
  {
    return isLoanable;
  }

  public double getValue()
  {
    return value;
  }


  public int getArtworkId()
  {
    return artworkId;
  }

  public String getArtworkName()
  {
    return artworkName;
  }
  /* Code from template attribute_IsBoolean */
  @Transient
  public boolean isIsLoanable()
  {
    return isLoanable;
  }
  /* Code from template association_GetOne */
  
  public Museum getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }
  /* Code from template association_GetMany */
  
  public Loan getLoan(int index)
  {
    Loan aLoan = loans.get(index);
    return aLoan;
  }
  
  public List<Loan> getLoans()
  {
    List<Loan> newLoans = Collections.unmodifiableList(loans);
    return newLoans;
  }

  public int numberOfLoans()
  {
    int number = loans.size();
    return number;
  }

  public boolean hasLoans()
  {
    boolean has = loans.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loans.indexOf(aLoan);
    return index;
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
      existingMuseum.removeArtwork(this);
    }
    museum.addArtwork(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setRoom(Room aRoom)
  {
    boolean wasSet = false;
    if (aRoom == null)
    {
      return wasSet;
    }

    Room existingRoom = room;
    room = aRoom;
    if (existingRoom != null && !existingRoom.equals(aRoom))
    {
      existingRoom.removeArtwork(this);
    }
    room.addArtwork(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(double aRentalFee, Date aStartDate, Date aEndDate, int aNumOfDays, Loan.LoanStatus aStatus, int aLoanId, Museum aMuseum, Customer aCustomer)
  {
    return new Loan(aRentalFee, aStartDate, aEndDate, aNumOfDays, aStatus, aLoanId, aMuseum, aCustomer, this);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    Artwork existingArtwork = aLoan.getArtwork();
    boolean isNewArtwork = existingArtwork != null && !this.equals(existingArtwork);
    if (isNewArtwork)
    {
      aLoan.setArtwork(this);
    }
    else
    {
      loans.add(aLoan);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoan, as it must always have a artwork
    if (!this.equals(aLoan.getArtwork()))
    {
      loans.remove(aLoan);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoanAt(Loan aLoan, int index)
  {  
    boolean wasAdded = false;
    if(addLoan(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loans.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Museum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeArtwork(this);
    }
    Room placeholderRoom = room;
    this.room = null;
    if(placeholderRoom != null)
    {
      placeholderRoom.removeArtwork(this);
    }
    for(int i=loans.size(); i > 0; i--)
    {
      Loan aLoan = loans.get(i - 1);
      aLoan.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isLoanable" + ":" + getIsLoanable()+ "," +
            "value" + ":" + getValue()+ "," +
            "artworkId" + ":" + getArtworkId()+ "," +
            "artworkName" + ":" + getArtworkName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "room = "+(getRoom()!=null?Integer.toHexString(System.identityHashCode(getRoom())):"null");
  }
}
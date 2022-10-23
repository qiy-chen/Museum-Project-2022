/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 58 "Museum.ump"
// line 133 "Museum.ump"
public class Artifact
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artifact Attributes
  private boolean isLoanable;

  //Artifact Associations
  private Museum museum;
  private Room room;
  private List<Loan> loans;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artifact(boolean aIsLoanable, Museum aMuseum, Room aRoom)
  {
    isLoanable = aIsLoanable;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create artifact due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddRoom = setRoom(aRoom);
    if (!didAddRoom)
    {
      throw new RuntimeException("Unable to create artifact due to room. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    loans = new ArrayList<Loan>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsLoanable(boolean aIsLoanable)
  {
    boolean wasSet = false;
    isLoanable = aIsLoanable;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsLoanable()
  {
    return isLoanable;
  }
  /* Code from template attribute_IsBoolean */
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
      existingMuseum.removeArtifact(this);
    }
    museum.addArtifact(this);
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
      existingRoom.removeArtifact(this);
    }
    room.addArtifact(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(Museum aMuseum, Customer aCustomer)
  {
    return new Loan(aMuseum, aCustomer, this);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    Artifact existingArtifact = aLoan.getArtifact();
    boolean isNewArtifact = existingArtifact != null && !this.equals(existingArtifact);
    if (isNewArtifact)
    {
      aLoan.setArtifact(this);
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
    //Unable to remove aLoan, as it must always have a artifact
    if (!this.equals(aLoan.getArtifact()))
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
      placeholderMuseum.removeArtifact(this);
    }
    Room placeholderRoom = room;
    this.room = null;
    if(placeholderRoom != null)
    {
      placeholderRoom.removeArtifact(this);
    }
    while (loans.size() > 0)
    {
      Loan aLoan = loans.get(loans.size() - 1);
      aLoan.delete();
      loans.remove(aLoan);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "isLoanable" + ":" + getIsLoanable()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "room = "+(getRoom()!=null?Integer.toHexString(System.identityHashCode(getRoom())):"null");
  }
}
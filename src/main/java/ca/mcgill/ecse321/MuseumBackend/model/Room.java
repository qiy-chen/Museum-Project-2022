/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// line 69 "../../../../../Museum.ump"
// line 149 "../../../../../Museum.ump"
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private int roomNumber;
  @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
  private int roomId;

  //Room Associations
  @ManyToOne
  private Museum museum;
  @OneToMany
  private List<Artwork> artworks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(int aRoomNumber, int aRoomId, Museum aMuseum)
  {
    roomNumber = aRoomNumber;
    roomId = aRoomId;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create room due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artworks = new ArrayList<Artwork>();
  }

  public Room() {
    
  }
  
  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomNumber(int aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomId(int aRoomId)
  {
    boolean wasSet = false;
    roomId = aRoomId;
    wasSet = true;
    return wasSet;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }


  public int getRoomId()
  {
    return roomId;
  }
  /* Code from template association_GetOne */
  public Museum getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetMany */
  
  public Artwork getArtwork(int index)
  {
    Artwork aArtwork = artworks.get(index);
    return aArtwork;
  }

  public List<Artwork> getArtworks()
  {
    List<Artwork> newArtworks = Collections.unmodifiableList(artworks);
    return newArtworks;
  }

  public int numberOfArtworks()
  {
    int number = artworks.size();
    return number;
  }

  public boolean hasArtworks()
  {
    boolean has = artworks.size() > 0;
    return has;
  }

  public int indexOfArtwork(Artwork aArtwork)
  {
    int index = artworks.indexOf(aArtwork);
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
      existingMuseum.removeRoom(this);
    }
    museum.addRoom(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtworks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Artwork addArtwork(boolean aIsLoanable, double aValue, int aArtworkId, String aArtworkName, Museum aMuseum)
  {
    return new Artwork(aIsLoanable, aValue, aArtworkId, aArtworkName, aMuseum, this);
  }

  public boolean addArtwork(Artwork aArtwork)
  {
    boolean wasAdded = false;
    if (artworks.contains(aArtwork)) { return false; }
    Room existingRoom = aArtwork.getRoom();
    boolean isNewRoom = existingRoom != null && !this.equals(existingRoom);
    if (isNewRoom)
    {
      aArtwork.setRoom(this);
    }
    else
    {
      artworks.add(aArtwork);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtwork(Artwork aArtwork)
  {
    boolean wasRemoved = false;
    //Unable to remove aArtwork, as it must always have a room
    if (!this.equals(aArtwork.getRoom()))
    {
      artworks.remove(aArtwork);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtworkAt(Artwork aArtwork, int index)
  {  
    boolean wasAdded = false;
    if(addArtwork(aArtwork))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtworks()) { index = numberOfArtworks() - 1; }
      artworks.remove(aArtwork);
      artworks.add(index, aArtwork);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtworkAt(Artwork aArtwork, int index)
  {
    boolean wasAdded = false;
    if(artworks.contains(aArtwork))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtworks()) { index = numberOfArtworks() - 1; }
      artworks.remove(aArtwork);
      artworks.add(index, aArtwork);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtworkAt(aArtwork, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Museum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeRoom(this);
    }
    for(int i=artworks.size(); i > 0; i--)
    {
      Artwork aArtwork = artworks.get(i - 1);
      aArtwork.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "roomNumber" + ":" + getRoomNumber()+ "," +
            "roomId" + ":" + getRoomId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null");
  }
}
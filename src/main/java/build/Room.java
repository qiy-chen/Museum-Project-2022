/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 53 "Museum.ump"
// line 127 "Museum.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Associations
  private List<RoomRole> roomRoles;
  private Museum museum;
  private List<Artifact> artifacts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(Museum aMuseum)
  {
    roomRoles = new ArrayList<RoomRole>();
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create room due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artifacts = new ArrayList<Artifact>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public RoomRole getRoomRole(int index)
  {
    RoomRole aRoomRole = roomRoles.get(index);
    return aRoomRole;
  }

  public List<RoomRole> getRoomRoles()
  {
    List<RoomRole> newRoomRoles = Collections.unmodifiableList(roomRoles);
    return newRoomRoles;
  }

  public int numberOfRoomRoles()
  {
    int number = roomRoles.size();
    return number;
  }

  public boolean hasRoomRoles()
  {
    boolean has = roomRoles.size() > 0;
    return has;
  }

  public int indexOfRoomRole(RoomRole aRoomRole)
  {
    int index = roomRoles.indexOf(aRoomRole);
    return index;
  }
  /* Code from template association_GetOne */
  public Museum getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetMany */
  public Artifact getArtifact(int index)
  {
    Artifact aArtifact = artifacts.get(index);
    return aArtifact;
  }

  public List<Artifact> getArtifacts()
  {
    List<Artifact> newArtifacts = Collections.unmodifiableList(artifacts);
    return newArtifacts;
  }

  public int numberOfArtifacts()
  {
    int number = artifacts.size();
    return number;
  }

  public boolean hasArtifacts()
  {
    boolean has = artifacts.size() > 0;
    return has;
  }

  public int indexOfArtifact(Artifact aArtifact)
  {
    int index = artifacts.indexOf(aArtifact);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRoomRoles()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addRoomRole(RoomRole aRoomRole)
  {
    boolean wasAdded = false;
    if (roomRoles.contains(aRoomRole)) { return false; }
    roomRoles.add(aRoomRole);
    if (aRoomRole.indexOfRoom(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aRoomRole.addRoom(this);
      if (!wasAdded)
      {
        roomRoles.remove(aRoomRole);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeRoomRole(RoomRole aRoomRole)
  {
    boolean wasRemoved = false;
    if (!roomRoles.contains(aRoomRole))
    {
      return wasRemoved;
    }

    int oldIndex = roomRoles.indexOf(aRoomRole);
    roomRoles.remove(oldIndex);
    if (aRoomRole.indexOfRoom(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aRoomRole.removeRoom(this);
      if (!wasRemoved)
      {
        roomRoles.add(oldIndex,aRoomRole);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomRoleAt(RoomRole aRoomRole, int index)
  {  
    boolean wasAdded = false;
    if(addRoomRole(aRoomRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRoomRoles()) { index = numberOfRoomRoles() - 1; }
      roomRoles.remove(aRoomRole);
      roomRoles.add(index, aRoomRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomRoleAt(RoomRole aRoomRole, int index)
  {
    boolean wasAdded = false;
    if(roomRoles.contains(aRoomRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRoomRoles()) { index = numberOfRoomRoles() - 1; }
      roomRoles.remove(aRoomRole);
      roomRoles.add(index, aRoomRole);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomRoleAt(aRoomRole, index);
    }
    return wasAdded;
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
  public static int minimumNumberOfArtifacts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Artifact addArtifact(boolean aIsLoanable, Museum aMuseum)
  {
    return new Artifact(aIsLoanable, aMuseum, this);
  }

  public boolean addArtifact(Artifact aArtifact)
  {
    boolean wasAdded = false;
    if (artifacts.contains(aArtifact)) { return false; }
    Room existingRoom = aArtifact.getRoom();
    boolean isNewRoom = existingRoom != null && !this.equals(existingRoom);
    if (isNewRoom)
    {
      aArtifact.setRoom(this);
    }
    else
    {
      artifacts.add(aArtifact);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtifact(Artifact aArtifact)
  {
    boolean wasRemoved = false;
    //Unable to remove aArtifact, as it must always have a room
    if (!this.equals(aArtifact.getRoom()))
    {
      artifacts.remove(aArtifact);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtifactAt(Artifact aArtifact, int index)
  {  
    boolean wasAdded = false;
    if(addArtifact(aArtifact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtifacts()) { index = numberOfArtifacts() - 1; }
      artifacts.remove(aArtifact);
      artifacts.add(index, aArtifact);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtifactAt(Artifact aArtifact, int index)
  {
    boolean wasAdded = false;
    if(artifacts.contains(aArtifact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtifacts()) { index = numberOfArtifacts() - 1; }
      artifacts.remove(aArtifact);
      artifacts.add(index, aArtifact);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtifactAt(aArtifact, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (roomRoles.size() > 0)
    {
      RoomRole aRoomRole = roomRoles.get(roomRoles.size() - 1);
      aRoomRole.delete();
      roomRoles.remove(aRoomRole);
    }
    
    Museum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeRoom(this);
    }
    for(int i=artifacts.size(); i > 0; i--)
    {
      Artifact aArtifact = artifacts.get(i - 1);
      aArtifact.delete();
    }
  }

}
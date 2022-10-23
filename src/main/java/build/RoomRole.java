/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 65 "Museum.ump"
// line 141 "Museum.ump"
public abstract class RoomRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomRole Associations
  private List<Room> rooms;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RoomRole()
  {
    rooms = new ArrayList<Room>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = rooms.get(index);
    return aRoom;
  }

  public List<Room> getRooms()
  {
    List<Room> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = rooms.indexOf(aRoom);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    rooms.add(aRoom);
    if (aRoom.indexOfRoomRole(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aRoom.addRoomRole(this);
      if (!wasAdded)
      {
        rooms.remove(aRoom);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeRoom(Room aRoom)
  {
    boolean wasRemoved = false;
    if (!rooms.contains(aRoom))
    {
      return wasRemoved;
    }

    int oldIndex = rooms.indexOf(aRoom);
    rooms.remove(oldIndex);
    if (aRoom.indexOfRoomRole(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aRoom.removeRoomRole(this);
      if (!wasRemoved)
      {
        rooms.add(oldIndex,aRoom);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(Room aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Room> copyOfRooms = new ArrayList<Room>(rooms);
    rooms.clear();
    for(Room aRoom : copyOfRooms)
    {
      aRoom.removeRoomRole(this);
    }
  }

}
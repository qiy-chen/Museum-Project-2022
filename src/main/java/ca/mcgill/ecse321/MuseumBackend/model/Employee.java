/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

// line 64 "../../../../../Museum.ump"
// line 144 "../../../../../Museum.ump"
@Entity
public class Employee extends PersonRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private List<Shift> shifts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(int aPersonRoleId)
  {
    shifts = new ArrayList<Shift>();
  }

  public Employee() {
	// TODO Auto-generated constructor stub
}

//------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  @ManyToMany
  public Shift getShift(int index)
  {
    Shift aShift = shifts.get(index);
    return aShift;
  }

  public List<Shift> getShifts()
  {
    List<Shift> newShifts = Collections.unmodifiableList(shifts);
    return newShifts;
  }

  public int numberOfShifts()
  {
    int number = shifts.size();
    return number;
  }

  public boolean hasShifts()
  {
    boolean has = shifts.size() > 0;
    return has;
  }

  public int indexOfShift(Shift aShift)
  {
    int index = shifts.indexOf(aShift);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShifts()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addShift(Shift aShift)
  {
    boolean wasAdded = false;
    if (shifts.contains(aShift)) { return false; }
    shifts.add(aShift);
    if (aShift.indexOfEmployee(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aShift.addEmployee(this);
      if (!wasAdded)
      {
        shifts.remove(aShift);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeShift(Shift aShift)
  {
    boolean wasRemoved = false;
    if (!shifts.contains(aShift))
    {
      return wasRemoved;
    }

    int oldIndex = shifts.indexOf(aShift);
    shifts.remove(oldIndex);
    if (aShift.indexOfEmployee(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aShift.removeEmployee(this);
      if (!wasRemoved)
      {
        shifts.add(oldIndex,aShift);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShiftAt(Shift aShift, int index)
  {  
    boolean wasAdded = false;
    if(addShift(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShiftAt(Shift aShift, int index)
  {
    boolean wasAdded = false;
    if(shifts.contains(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShiftAt(aShift, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Shift> copyOfShifts = new ArrayList<Shift>(shifts);
    shifts.clear();
    for(Shift aShift : copyOfShifts)
    {
      aShift.removeEmployee(this);
    }
    super.delete();
  }

}
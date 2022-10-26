/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;

// line 62 "../../../../../Museum.ump"
// line 143 "../../../../../Museum.ump"
public class Employee extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private List<WorkDay> workDaies;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(UUID aUserRoleId)
  {
    super(aUserRoleId);
    workDaies = new ArrayList<WorkDay>();
  }

  public Employee() {
	// TODO Auto-generated constructor stub
}

//------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public WorkDay getWorkDay(int index)
  {
    WorkDay aWorkDay = workDaies.get(index);
    return aWorkDay;
  }

  public List<WorkDay> getWorkDaies()
  {
    List<WorkDay> newWorkDaies = Collections.unmodifiableList(workDaies);
    return newWorkDaies;
  }

  public int numberOfWorkDaies()
  {
    int number = workDaies.size();
    return number;
  }

  public boolean hasWorkDaies()
  {
    boolean has = workDaies.size() > 0;
    return has;
  }

  public int indexOfWorkDay(WorkDay aWorkDay)
  {
    int index = workDaies.indexOf(aWorkDay);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorkDaies()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addWorkDay(WorkDay aWorkDay)
  {
    boolean wasAdded = false;
    if (workDaies.contains(aWorkDay)) { return false; }
    workDaies.add(aWorkDay);
    if (aWorkDay.indexOfEmployee(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aWorkDay.addEmployee(this);
      if (!wasAdded)
      {
        workDaies.remove(aWorkDay);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeWorkDay(WorkDay aWorkDay)
  {
    boolean wasRemoved = false;
    if (!workDaies.contains(aWorkDay))
    {
      return wasRemoved;
    }

    int oldIndex = workDaies.indexOf(aWorkDay);
    workDaies.remove(oldIndex);
    if (aWorkDay.indexOfEmployee(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aWorkDay.removeEmployee(this);
      if (!wasRemoved)
      {
        workDaies.add(oldIndex,aWorkDay);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkDayAt(WorkDay aWorkDay, int index)
  {  
    boolean wasAdded = false;
    if(addWorkDay(aWorkDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkDaies()) { index = numberOfWorkDaies() - 1; }
      workDaies.remove(aWorkDay);
      workDaies.add(index, aWorkDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkDayAt(WorkDay aWorkDay, int index)
  {
    boolean wasAdded = false;
    if(workDaies.contains(aWorkDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkDaies()) { index = numberOfWorkDaies() - 1; }
      workDaies.remove(aWorkDay);
      workDaies.add(index, aWorkDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkDayAt(aWorkDay, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<WorkDay> copyOfWorkDaies = new ArrayList<WorkDay>(workDaies);
    workDaies.clear();
    for(WorkDay aWorkDay : copyOfWorkDaies)
    {
      aWorkDay.removeEmployee(this);
    }
    super.delete();
  }

}
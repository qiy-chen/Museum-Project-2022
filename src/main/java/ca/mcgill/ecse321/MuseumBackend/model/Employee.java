/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;

import javax.persistence.Entity;

// line 62 "../../../../../Museum.ump"
// line 143 "../../../../../Museum.ump"
@Entity
public class Employee extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private List<WorkDay> workDays;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(UUID aUserRoleId)
  {
    super(aUserRoleId);
    workDays = new ArrayList<WorkDay>();
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
    WorkDay aWorkDay = workDays.get(index);
    return aWorkDay;
  }

  public List<WorkDay> getWorkDaies()
  {
    List<WorkDay> newWorkDaies = Collections.unmodifiableList(workDays);
    return newWorkDaies;
  }

  public int numberOfWorkDaies()
  {
    int number = workDays.size();
    return number;
  }

  public boolean hasWorkDaies()
  {
    boolean has = workDays.size() > 0;
    return has;
  }

  public int indexOfWorkDay(WorkDay aWorkDay)
  {
    int index = workDays.indexOf(aWorkDay);
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
    if (workDays.contains(aWorkDay)) { return false; }
    workDays.add(aWorkDay);
    if (aWorkDay.indexOfEmployee(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aWorkDay.addEmployee(this);
      if (!wasAdded)
      {
        workDays.remove(aWorkDay);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeWorkDay(WorkDay aWorkDay)
  {
    boolean wasRemoved = false;
    if (!workDays.contains(aWorkDay))
    {
      return wasRemoved;
    }

    int oldIndex = workDays.indexOf(aWorkDay);
    workDays.remove(oldIndex);
    if (aWorkDay.indexOfEmployee(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aWorkDay.removeEmployee(this);
      if (!wasRemoved)
      {
        workDays.add(oldIndex,aWorkDay);
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
      workDays.remove(aWorkDay);
      workDays.add(index, aWorkDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkDayAt(WorkDay aWorkDay, int index)
  {
    boolean wasAdded = false;
    if(workDays.contains(aWorkDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkDaies()) { index = numberOfWorkDaies() - 1; }
      workDays.remove(aWorkDay);
      workDays.add(index, aWorkDay);
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
    ArrayList<WorkDay> copyOfWorkDaies = new ArrayList<WorkDay>(workDays);
    workDays.clear();
    for(WorkDay aWorkDay : copyOfWorkDaies)
    {
      aWorkDay.removeEmployee(this);
    }
    super.delete();
  }

}
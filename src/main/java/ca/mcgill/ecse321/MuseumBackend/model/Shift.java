/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// line 34 "../../../../../Museum.ump"
// line 127 "../../../../../Museum.ump"
@Entity
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int workDayId;

  //Shift Associations
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Employee> employees;
  @ManyToOne
  private Museum museum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shift(LocalDateTime aStartTime, LocalDateTime aEndTime, int aWorkDayId, Museum aMuseum)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    workDayId = aWorkDayId;
    employees = new ArrayList<Employee>();
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create shift due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  
  public Shift() {
    employees = new ArrayList<>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(LocalDateTime aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(LocalDateTime aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setWorkDayId(int aWorkDayId)
  {
    boolean wasSet = false;
    workDayId = aWorkDayId;
    wasSet = true;
    return wasSet;
  }

  public LocalDateTime getStartTime()
  {
    return startTime;
  }

  public LocalDateTime getEndTime()
  {
    return endTime;
  }


  public int getWorkDayId()
  {
    return workDayId;
  }
  /* Code from template association_GetMany */
 
  public Employee getEmployee(int index)
  {
    Employee aEmployee = employees.get(index);
    return aEmployee;
  }

  public List<Employee> getEmployees()
  {
    List<Employee> newEmployees = Collections.unmodifiableList(employees);
    return newEmployees;
  }

  public int numberOfEmployees()
  {
    int number = employees.size();
    return number;
  }

  public boolean hasEmployees()
  {
    boolean has = employees.size() > 0;
    return has;
  }

  public int indexOfEmployee(Employee aEmployee)
  {
    int index = employees.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_GetOne */
  public Museum getMuseum()
  {
    return museum;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    employees.add(aEmployee);
    if (aEmployee.indexOfShift(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aEmployee.addShift(this);
      if (!wasAdded)
      {
        employees.remove(aEmployee);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  @PreRemove
  private void removeShiftFromEmployees() {
    List<Employee> employeeList = new ArrayList<>();
    for(Employee e: employees) {
      employeeList.add(e);
    }
    for(Employee e : employeeList) {
      if(e.getShifts().contains(this))removeEmployee(e);
    }
  }

  public boolean removeEmployee(Employee aEmployee)
  {
    boolean wasRemoved = false;
    if (!employees.contains(aEmployee))
    {
      return wasRemoved;
    }

    int oldIndex = employees.indexOf(aEmployee);
    employees.remove(oldIndex);
    if (aEmployee.indexOfShift(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aEmployee.removeShift(this);
      if (!wasRemoved)
      {
        employees.add(oldIndex,aEmployee);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeeAt(Employee aEmployee, int index)
  {  
    boolean wasAdded = false;
    if(addEmployee(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeeAt(Employee aEmployee, int index)
  {
    boolean wasAdded = false;
    if(employees.contains(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeeAt(aEmployee, index);
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
      existingMuseum.removeShift(this);
    }
    museum.addShift(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Employee> copyOfEmployees = new ArrayList<Employee>(employees);
    employees.clear();
    for(Employee aEmployee : copyOfEmployees)
    {
      aEmployee.removeShift(this);
    }
    Museum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeShift(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "workDayId" + ":" + getWorkDayId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null");
  }
}
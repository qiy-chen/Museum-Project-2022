/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import javax.persistence.*;
import java.util.*;

// line 11 "../../../../../Museum.ump"
// line 115 "../../../../../Museum.ump"
@Entity
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String email;
  private String password;
  private String name;

  //Person Associations
  private Museum museum;
  private List<PersonRole> personRoles;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Person(String aEmail, String aPassword, String aName, Museum aMuseum)
  {
    email = aEmail;
    password = aPassword;
    name = aName;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create person due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    personRoles = new ArrayList<PersonRole>();
  }

  //------------------------
  // INTERFACE
  //------------------------



public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  @ManyToOne
  public Museum getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetMany */
  @OneToMany
  public PersonRole getPersonRole(int index)
  {
    PersonRole aPersonRole = personRoles.get(index);
    return aPersonRole;
  }

  public List<PersonRole> getPersonRoles()
  {
    List<PersonRole> newPersonRoles = Collections.unmodifiableList(personRoles);
    return newPersonRoles;
  }

  public int numberOfPersonRoles()
  {
    int number = personRoles.size();
    return number;
  }

  public boolean hasPersonRoles()
  {
    boolean has = personRoles.size() > 0;
    return has;
  }

  public int indexOfPersonRole(PersonRole aPersonRole)
  {
    int index = personRoles.indexOf(aPersonRole);
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
      existingMuseum.removePerson(this);
    }
    museum.addPerson(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPersonRoles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addPersonRole(PersonRole aPersonRole)
  {
    boolean wasAdded = false;
    if (personRoles.contains(aPersonRole)) { return false; }
    Person existingPerson = aPersonRole.getPerson();
    boolean isNewPerson = existingPerson != null && !this.equals(existingPerson);
    if (isNewPerson)
    {
      aPersonRole.setPerson(this);
    }
    else
    {
      personRoles.add(aPersonRole);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePersonRole(PersonRole aPersonRole)
  {
    boolean wasRemoved = false;
    //Unable to remove aPersonRole, as it must always have a person
    if (!this.equals(aPersonRole.getPerson()))
    {
      personRoles.remove(aPersonRole);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPersonRoleAt(PersonRole aPersonRole, int index)
  {
    boolean wasAdded = false;
    if(addPersonRole(aPersonRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersonRoles()) { index = numberOfPersonRoles() - 1; }
      personRoles.remove(aPersonRole);
      personRoles.add(index, aPersonRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePersonRoleAt(PersonRole aPersonRole, int index)
  {
    boolean wasAdded = false;
    if(personRoles.contains(aPersonRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersonRoles()) { index = numberOfPersonRoles() - 1; }
      personRoles.remove(aPersonRole);
      personRoles.add(index, aPersonRole);
      wasAdded = true;
    }
    else
    {
      wasAdded = addPersonRoleAt(aPersonRole, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Museum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removePerson(this);
    }
    while (personRoles.size() > 0)
    {
      PersonRole aPersonRole = personRoles.get(personRoles.size() - 1);
      aPersonRole.delete();
      personRoles.remove(aPersonRole);
    }

  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null");
  }
}
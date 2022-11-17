/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import javax.persistence.*;

// line 27 "../../../../../Museum.ump"
// line 120 "../../../../../Museum.ump"
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PersonRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PersonRole Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int personRoleId;

  //PersonRole Associations
  @ManyToOne
  private Person person;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PersonRole(int aPersonRoleId, Person aPerson)
  {
    personRoleId = aPersonRoleId;
    boolean didAddPerson = setPerson(aPerson);
    if (!didAddPerson)
    {
      throw new RuntimeException("Unable to create personRole due to person. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  
  public PersonRole() {
    
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPersonRoleId(int aPersonRoleId)
  {
    boolean wasSet = false;
    personRoleId = aPersonRoleId;
    wasSet = true;
    return wasSet;
  }

  public int getPersonRoleId()
  {
    return personRoleId;
  }
  /* Code from template association_GetOne */
  public Person getPerson()
  {
    return person;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPerson(Person aPerson)
  {
    boolean wasSet = false;
    if (aPerson == null)
    {
      return wasSet;
    }

    Person existingPerson = person;
    person = aPerson;
    if (existingPerson != null && !existingPerson.equals(aPerson))
    {
      existingPerson.removePersonRole(this);
    }
    person.addPersonRole(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Person placeholderPerson = person;
    this.person = null;
    if(placeholderPerson != null)
    {
      placeholderPerson.removePersonRole(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "personRoleId" + ":" + getPersonRoleId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "person = "+(getPerson()!=null?Integer.toHexString(System.identityHashCode(getPerson())):"null");
  }
}
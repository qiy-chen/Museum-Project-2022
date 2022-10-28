/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;
import javax.persistence.Entity;

// line 59 "../../../../../Museum.ump"
// line 139 "../../../../../Museum.ump"
@Entity
public class Admin extends PersonRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Admin() {}
  public Admin(int aPersonRoleId, Person person)
  {
    super(aPersonRoleId, person);
  }

  public Admin() {
    super();
  }
  
  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}
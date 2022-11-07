/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;

import javax.persistence.Entity;

// line 88 "../../../../../Museum.ump"
// line 156 "../../../../../Museum.ump"
@Entity
public class Storage extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Storage(int aRoomNumber, int aRoomId, Museum aMuseum)
  {
    super(aRoomNumber, aRoomId, aMuseum);
  }
  
  public Storage() {
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
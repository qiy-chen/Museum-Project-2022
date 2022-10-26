/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;

// line 94 "../../../../../Museum.ump"
// line 161 "../../../../../Museum.ump"
public class Display extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Display Attributes
  private int maxArtifacts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Display(int aRoomNumber, int aRoomId, Museum aMuseum, int aMaxArtifacts)
  {
    super(aRoomNumber, aRoomId, aMuseum);
    maxArtifacts = aMaxArtifacts;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMaxArtifacts(int aMaxArtifacts)
  {
    boolean wasSet = false;
    maxArtifacts = aMaxArtifacts;
    wasSet = true;
    return wasSet;
  }

  public int getMaxArtifacts()
  {
    return maxArtifacts;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "maxArtifacts" + ":" + getMaxArtifacts()+ "]";
  }
}
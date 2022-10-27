/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;
import javax.persistence.Entity;

// line 94 "../../../../../Museum.ump"
// line 161 "../../../../../Museum.ump"
@Entity
public class Display extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Display Attributes
  private int maxArtworks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Display(int aRoomNumber, int aRoomId, Museum aMuseum, int aMaxArtworks)
  {
    super(aRoomNumber, aRoomId, aMuseum);
    maxArtworks = aMaxArtworks;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMaxArtworks(int aMaxArtworks)
  {
    boolean wasSet = false;
    maxArtworks = aMaxArtworks;
    wasSet = true;
    return wasSet;
  }

  public int getMaxArtworks()
  {
    return maxArtworks;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "maxArtworks" + ":" + getMaxArtworks()+ "]";
  }
}
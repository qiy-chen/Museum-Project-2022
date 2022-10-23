/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 32 "Museum.ump"
public class Loan
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Associations
  private Museum museum;
  private Customer customer;
  private Artifact artifact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(Museum aMuseum, Customer aCustomer, Artifact aArtifact)
  {
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create loan due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create loan due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddArtifact = setArtifact(aArtifact);
    if (!didAddArtifact)
    {
      throw new RuntimeException("Unable to create loan due to artifact. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Museum getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public Artifact getArtifact()
  {
    return artifact;
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
      existingMuseum.removeLoan(this);
    }
    museum.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeLoan(this);
    }
    customer.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setArtifact(Artifact aArtifact)
  {
    boolean wasSet = false;
    if (aArtifact == null)
    {
      return wasSet;
    }

    Artifact existingArtifact = artifact;
    artifact = aArtifact;
    if (existingArtifact != null && !existingArtifact.equals(aArtifact))
    {
      existingArtifact.removeLoan(this);
    }
    artifact.addLoan(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Museum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeLoan(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeLoan(this);
    }
    Artifact placeholderArtifact = artifact;
    this.artifact = null;
    if(placeholderArtifact != null)
    {
      placeholderArtifact.removeLoan(this);
    }
  }

}
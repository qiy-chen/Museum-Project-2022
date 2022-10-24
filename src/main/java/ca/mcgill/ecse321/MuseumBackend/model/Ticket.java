/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;

// line 16 "../../../../../Museum.ump"
public class Ticket
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ticket Attributes
  private double price;
  private UUID ticketId;

  //Ticket Associations
  private Museum museum;
  private WorkDay workDay;
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ticket(double aPrice, UUID aTicketId, Museum aMuseum, WorkDay aWorkDay, Customer aCustomer)
  {
    price = aPrice;
    ticketId = aTicketId;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create ticket due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddWorkDay = setWorkDay(aWorkDay);
    if (!didAddWorkDay)
    {
      throw new RuntimeException("Unable to create ticket due to workDay. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create ticket due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setTicketId(UUID aTicketId)
  {
    boolean wasSet = false;
    ticketId = aTicketId;
    wasSet = true;
    return wasSet;
  }

  public double getPrice()
  {
    return price;
  }

  public UUID getTicketId()
  {
    return ticketId;
  }
  /* Code from template association_GetOne */
  public Museum getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetOne */
  public WorkDay getWorkDay()
  {
    return workDay;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
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
      existingMuseum.removeTicket(this);
    }
    museum.addTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWorkDay(WorkDay aWorkDay)
  {
    boolean wasSet = false;
    if (aWorkDay == null)
    {
      return wasSet;
    }

    WorkDay existingWorkDay = workDay;
    workDay = aWorkDay;
    if (existingWorkDay != null && !existingWorkDay.equals(aWorkDay))
    {
      existingWorkDay.removeTicket(this);
    }
    workDay.addTicket(this);
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
      existingCustomer.removeTicket(this);
    }
    customer.addTicket(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Museum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeTicket(this);
    }
    WorkDay placeholderWorkDay = workDay;
    this.workDay = null;
    if(placeholderWorkDay != null)
    {
      placeholderWorkDay.removeTicket(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeTicket(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ticketId" + "=" + (getTicketId() != null ? !getTicketId().equals(this)  ? getTicketId().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "workDay = "+(getWorkDay()!=null?Integer.toHexString(System.identityHashCode(getWorkDay())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}
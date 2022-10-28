/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.sql.Date;

// line 54 "../../../../../Museum.ump"
// line 134 "../../../../../Museum.ump"
@Entity
public class Customer extends PersonRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Associations
  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Ticket> tickets;
  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Loan> loans;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(int aPersonRoleId,Person person)
  {
    super(aPersonRoleId, person);
    tickets = new ArrayList<Ticket>();
    loans = new ArrayList<Loan>();
  }
  
  public Customer() {
    super();
    tickets = new ArrayList<Ticket>();
    loans = new ArrayList<Loan>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  
  public Ticket getTicket(int index)
  {
    Ticket aTicket = tickets.get(index);
    return aTicket;
  }
  
  public List<Ticket> getTickets()
  {
    List<Ticket> newTickets = Collections.unmodifiableList(tickets);
    return newTickets;
  }

  public int numberOfTickets()
  {
    int number = tickets.size();
    return number;
  }

  public boolean hasTickets()
  {
    boolean has = tickets.size() > 0;
    return has;
  }

  public int indexOfTicket(Ticket aTicket)
  {
    int index = tickets.indexOf(aTicket);
    return index;
  }
  /* Code from template association_GetMany */

  public Loan getLoan(int index)
  {
    Loan aLoan = loans.get(index);
    return aLoan;
  }
  
  public List<Loan> getLoans()
  {
    List<Loan> newLoans = Collections.unmodifiableList(loans);
    return newLoans;
  }

  public int numberOfLoans()
  {
    int number = loans.size();
    return number;
  }

  public boolean hasLoans()
  {
    boolean has = loans.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loans.indexOf(aLoan);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Ticket addTicket(double aPrice, int aTicketId, Date aTicketDate, Museum aMuseum)
  {
    return new Ticket(aPrice, aTicketId, aTicketDate, aMuseum, this);
  }

  public boolean addTicket(Ticket aTicket)
  {
    boolean wasAdded = false;
    if (tickets.contains(aTicket)) { return false; }
    Customer existingCustomer = aTicket.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aTicket.setCustomer(this);
    }
    else
    {
      tickets.add(aTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicket(Ticket aTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aTicket, as it must always have a customer
    if (!this.equals(aTicket.getCustomer()))
    {
      tickets.remove(aTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketAt(Ticket aTicket, int index)
  {  
    boolean wasAdded = false;
    if(addTicket(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketAt(Ticket aTicket, int index)
  {
    boolean wasAdded = false;
    if(tickets.contains(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketAt(aTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(double aRentalFee, Date aStartDate, Date aEndDate, int aNumOfDays, Loan.LoanStatus aStatus, int aLoanId, Museum aMuseum, Artwork aArtwork)
  {
    return new Loan(aRentalFee, aStartDate, aEndDate, aNumOfDays, aStatus, aLoanId, aMuseum, this, aArtwork);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    Customer existingCustomer = aLoan.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aLoan.setCustomer(this);
    }
    else
    {
      loans.add(aLoan);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoan, as it must always have a customer
    if (!this.equals(aLoan.getCustomer()))
    {
      loans.remove(aLoan);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoanAt(Loan aLoan, int index)
  {  
    boolean wasAdded = false;
    if(addLoan(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loans.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=tickets.size(); i > 0; i--)
    {
      Ticket aTicket = tickets.get(i - 1);
      aTicket.delete();
    }
    while (loans.size() > 0)
    {
      Loan aLoan = loans.get(loans.size() - 1);
      aLoan.delete();
      loans.remove(aLoan);
    }
    
    super.delete();
  }

}
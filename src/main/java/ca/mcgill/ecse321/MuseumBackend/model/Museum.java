/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.MuseumBackend.model;
import java.util.*;
import java.sql.Date;

// line 2 "../../../../../Museum.ump"
// line 105 "../../../../../Museum.ump"
public class Museum
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Museum Associations
  private List<Room> rooms;
  private List<WorkDay> workDaies;
  private List<User> users;
  private List<Ticket> tickets;
  private List<Loan> loans;
  private List<Artifact> artifacts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Museum()
  {
    rooms = new ArrayList<Room>();
    workDaies = new ArrayList<WorkDay>();
    users = new ArrayList<User>();
    tickets = new ArrayList<Ticket>();
    loans = new ArrayList<Loan>();
    artifacts = new ArrayList<Artifact>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = rooms.get(index);
    return aRoom;
  }

  public List<Room> getRooms()
  {
    List<Room> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = rooms.indexOf(aRoom);
    return index;
  }
  /* Code from template association_GetMany */
  public WorkDay getWorkDay(int index)
  {
    WorkDay aWorkDay = workDaies.get(index);
    return aWorkDay;
  }

  public List<WorkDay> getWorkDaies()
  {
    List<WorkDay> newWorkDaies = Collections.unmodifiableList(workDaies);
    return newWorkDaies;
  }

  public int numberOfWorkDaies()
  {
    int number = workDaies.size();
    return number;
  }

  public boolean hasWorkDaies()
  {
    boolean has = workDaies.size() > 0;
    return has;
  }

  public int indexOfWorkDay(WorkDay aWorkDay)
  {
    int index = workDaies.indexOf(aWorkDay);
    return index;
  }
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
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
  /* Code from template association_GetMany */
  public Artifact getArtifact(int index)
  {
    Artifact aArtifact = artifacts.get(index);
    return aArtifact;
  }

  public List<Artifact> getArtifacts()
  {
    List<Artifact> newArtifacts = Collections.unmodifiableList(artifacts);
    return newArtifacts;
  }

  public int numberOfArtifacts()
  {
    int number = artifacts.size();
    return number;
  }

  public boolean hasArtifacts()
  {
    boolean has = artifacts.size() > 0;
    return has;
  }

  public int indexOfArtifact(Artifact aArtifact)
  {
    int index = artifacts.indexOf(aArtifact);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Room addRoom(int aRoomNumber, UUID aRoomId)
  {
    return new Room(aRoomNumber, aRoomId, this);
  }

  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    Museum existingMuseum = aRoom.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aRoom.setMuseum(this);
    }
    else
    {
      rooms.add(aRoom);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoom(Room aRoom)
  {
    boolean wasRemoved = false;
    //Unable to remove aRoom, as it must always have a museum
    if (!this.equals(aRoom.getMuseum()))
    {
      rooms.remove(aRoom);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(Room aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorkDaies()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WorkDay addWorkDay(Date aStartTime, Date aEndTime, UUID aWorkDayId)
  {
    return new WorkDay(aStartTime, aEndTime, aWorkDayId, this);
  }

  public boolean addWorkDay(WorkDay aWorkDay)
  {
    boolean wasAdded = false;
    if (workDaies.contains(aWorkDay)) { return false; }
    Museum existingMuseum = aWorkDay.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aWorkDay.setMuseum(this);
    }
    else
    {
      workDaies.add(aWorkDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWorkDay(WorkDay aWorkDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aWorkDay, as it must always have a museum
    if (!this.equals(aWorkDay.getMuseum()))
    {
      workDaies.remove(aWorkDay);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkDayAt(WorkDay aWorkDay, int index)
  {  
    boolean wasAdded = false;
    if(addWorkDay(aWorkDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkDaies()) { index = numberOfWorkDaies() - 1; }
      workDaies.remove(aWorkDay);
      workDaies.add(index, aWorkDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkDayAt(WorkDay aWorkDay, int index)
  {
    boolean wasAdded = false;
    if(workDaies.contains(aWorkDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkDaies()) { index = numberOfWorkDaies() - 1; }
      workDaies.remove(aWorkDay);
      workDaies.add(index, aWorkDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkDayAt(aWorkDay, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aEmail, String aPassword, String aName)
  {
    return new User(aEmail, aPassword, aName, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    Museum existingMuseum = aUser.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aUser.setMuseum(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a museum
    if (!this.equals(aUser.getMuseum()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Ticket addTicket(double aPrice, UUID aTicketId, WorkDay aWorkDay, Customer aCustomer)
  {
    return new Ticket(aPrice, aTicketId, this, aWorkDay, aCustomer);
  }

  public boolean addTicket(Ticket aTicket)
  {
    boolean wasAdded = false;
    if (tickets.contains(aTicket)) { return false; }
    Museum existingMuseum = aTicket.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aTicket.setMuseum(this);
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
    //Unable to remove aTicket, as it must always have a museum
    if (!this.equals(aTicket.getMuseum()))
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
  public Loan addLoan(double aRentalFee, Date aStartDate, Date aEndDate, int aNumOfDays, Loan.LoanStatus aStatus, UUID aLoanId, Customer aCustomer, Artifact aArtifact)
  {
    return new Loan(aRentalFee, aStartDate, aEndDate, aNumOfDays, aStatus, aLoanId, this, aCustomer, aArtifact);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    Museum existingMuseum = aLoan.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aLoan.setMuseum(this);
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
    //Unable to remove aLoan, as it must always have a museum
    if (!this.equals(aLoan.getMuseum()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtifacts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Artifact addArtifact(boolean aIsLoanable, double aValue, UUID aArtifactId, Room aRoom)
  {
    return new Artifact(aIsLoanable, aValue, aArtifactId, this, aRoom);
  }

  public boolean addArtifact(Artifact aArtifact)
  {
    boolean wasAdded = false;
    if (artifacts.contains(aArtifact)) { return false; }
    Museum existingMuseum = aArtifact.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aArtifact.setMuseum(this);
    }
    else
    {
      artifacts.add(aArtifact);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtifact(Artifact aArtifact)
  {
    boolean wasRemoved = false;
    //Unable to remove aArtifact, as it must always have a museum
    if (!this.equals(aArtifact.getMuseum()))
    {
      artifacts.remove(aArtifact);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtifactAt(Artifact aArtifact, int index)
  {  
    boolean wasAdded = false;
    if(addArtifact(aArtifact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtifacts()) { index = numberOfArtifacts() - 1; }
      artifacts.remove(aArtifact);
      artifacts.add(index, aArtifact);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtifactAt(Artifact aArtifact, int index)
  {
    boolean wasAdded = false;
    if(artifacts.contains(aArtifact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtifacts()) { index = numberOfArtifacts() - 1; }
      artifacts.remove(aArtifact);
      artifacts.add(index, aArtifact);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtifactAt(aArtifact, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (rooms.size() > 0)
    {
      Room aRoom = rooms.get(rooms.size() - 1);
      aRoom.delete();
      rooms.remove(aRoom);
    }
    
    while (workDaies.size() > 0)
    {
      WorkDay aWorkDay = workDaies.get(workDaies.size() - 1);
      aWorkDay.delete();
      workDaies.remove(aWorkDay);
    }
    
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (tickets.size() > 0)
    {
      Ticket aTicket = tickets.get(tickets.size() - 1);
      aTicket.delete();
      tickets.remove(aTicket);
    }
    
    while (loans.size() > 0)
    {
      Loan aLoan = loans.get(loans.size() - 1);
      aLoan.delete();
      loans.remove(aLoan);
    }
    
    while (artifacts.size() > 0)
    {
      Artifact aArtifact = artifacts.get(artifacts.size() - 1);
      aArtifact.delete();
      artifacts.remove(aArtifact);
    }
    
  }

}
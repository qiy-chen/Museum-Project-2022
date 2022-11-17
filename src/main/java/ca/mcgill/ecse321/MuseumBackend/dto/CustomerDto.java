package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.PersonRole;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;

public class CustomerDto {
  private int id;
  private Person person;
  private List<Loan> loans;
  private List<Ticket> tickets;
  
  public CustomerDto(Customer customer) {
    super();
    this.id = customer.getPersonRoleId();
    this.person = customer.getPerson();
    this.loans = customer.getLoans();
    this.tickets = customer.getTickets();
  }
}



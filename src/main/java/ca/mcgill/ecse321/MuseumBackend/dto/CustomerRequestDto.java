package ca.mcgill.ecse321.MuseumBackend.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Loan;

import ca.mcgill.ecse321.MuseumBackend.service.PersonService;
import ca.mcgill.ecse321.MuseumBackend.service.LoanService;

public class CustomerRequestDto {

  @Autowired
  private PersonService personService;
  @Autowired
  private LoanService loanService;

  // attributes
  private PersonDto persondto;
  private int[] loanIDs;
  private int customerId;

  public CustomerRequestDto() {}
  public CustomerRequestDto(PersonDto persondto, int id, int[] loanIDs) {
    this.persondto = persondto;
    this.customerId = id;
    this.loanIDs = loanIDs;
  }
  
  public void setPersonDto(PersonDto persondto) {
    this.persondto = persondto;
  }

  public PersonDto getPersonDto() {
    return this.persondto;
  }

  public void setCustomerId(int id) {
    this.customerId = id;
  }

  public int getCustomerId() {
    return this.customerId;
  }
  public void setCustomerLoanIDs(int[] loanIDs) {
    this.loanIDs = loanIDs;
  }

  public int[] getCustomerLoans() {
    return this.loanIDs;
  }

  public Customer toModel() {
    Customer customer = new Customer();
    Person person = persondto.toModel();
    customer.setPerson(person);
    customer.setPersonRoleId(customerId);
    if (this.loanIDs!=null) {
      for (int loanID: this.loanIDs) {
        Loan loan = loanService.getLoanById(loanID);
        if (loan!=null) customer.addLoan(loan);
      }
    }


    return customer;
  }
}

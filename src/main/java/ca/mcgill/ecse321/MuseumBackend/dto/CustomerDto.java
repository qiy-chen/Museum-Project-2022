package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.PersonRole;

public class CustomerDto {
  private PersonRole personRole;
  private Person person;
  
  public CustomerDto() {
    
  }
  public CustomerDto(PersonRole aPersonRole, Person aPerson) {
    this.personRole = aPersonRole;
    this.person = aPerson;
  }
  public PersonRole getPersonRole() {
    return personRole;
  }
  public Person getPerson() {
    return person;
  }
}



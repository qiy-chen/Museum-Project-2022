package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.PersonRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.PersonResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
public class PersonRestController {
    @Autowired
    private PersonService service;
    @Autowired
    private MuseumService museumService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/person/{email}")
    public ResponseEntity<PersonResponseDto> getPersonByEmail(@PathVariable String email) throws IllegalArgumentException {
        return new ResponseEntity<>(convertToResponseDto(service.getPersonByEmail(email)), HttpStatus.OK);
    }

    @GetMapping(value = "/person")
    public ResponseEntity<PersonResponseDto[]> getPeople() throws IllegalArgumentException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<Person> people = service.getPeople();
        PersonResponseDto[] personResponses = new PersonResponseDto[people.size()];
        int i =0;
        for(Person person:people) {
            personResponses[i] = convertToResponseDto(person);
            i++;
        }
        return new ResponseEntity<>(personResponses,httpHeaders,HttpStatus.OK);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonRequestDto personRequestDto) throws IllegalArgumentException {
        Person person = personRequestDto.toModel(museumService.getMuseumById(personRequestDto.getMuseumId()));
        for(int personRoleId : personRequestDto.getPersonRoleIds()) {
            try{
                Employee employee = employeeService.getEmployeeById(personRoleId);
                person.addPersonRole(employee);
            } catch (MuseumBackendException exception) {}
            try {
                Customer customer = customerService.getCustomerById(personRoleId);
                person.addPersonRole(customer);
            } catch (MuseumBackendException exception) {}
            try {
                Admin admin = adminService.getAdminById(personRoleId);
                person.addPersonRole(admin);
            } catch (MuseumBackendException exception) {}
        }
        return new ResponseEntity<>(convertToResponseDto(service.createPerson(person)),HttpStatus.CREATED);
    }
    @PutMapping(value = "/person/{email}/")
    public void changePersonNameAndPassword(@PathVariable String email, @RequestBody Map<String, String> inputMap) throws IllegalArgumentException {
        String name = inputMap.get("name");
        String password = inputMap.get("password");
        if(!Objects.equals(service.getPersonByEmail(email).getName(), name))service.changePersonName(email,name);
        if(!Objects.equals(service.getPersonByEmail(email).getPassword(), password))service.changePersonPassword(email,password);
    }

    @PutMapping(value = "/person/{email}")
    public void deletePerson(@PathVariable String email) throws  IllegalArgumentException {
        service.deletePerson(email);
    }
    @GetMapping(value = "/person/person_roles/{email}")
    public ResponseEntity<Integer[]> getAllPersonPersonRoles(@PathVariable String email) {
        return new ResponseEntity<>(convertToResponseDto(service.getPersonByEmail(email)).getPersonRoleIds().toArray(new Integer[0]), HttpStatus.OK);
    }

    private PersonResponseDto convertToResponseDto(Person p) {
        if(p==null)throw new IllegalArgumentException("There is no such Person!");
        return new PersonResponseDto(p.getEmail(),p.getPassword(),p.getName(),p.getMuseum().getMuseumId(),p.getPersonRoles());
    }
}

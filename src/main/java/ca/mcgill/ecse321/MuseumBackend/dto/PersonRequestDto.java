package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.*;
import ca.mcgill.ecse321.MuseumBackend.service.AdminService;
import ca.mcgill.ecse321.MuseumBackend.service.CustomerService;
import ca.mcgill.ecse321.MuseumBackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PersonRequestDto {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AdminService adminService;

    private String email;
    private String password;
    private String name;
    private Museum museum;
    private List<Integer> personRoleIds = new ArrayList<>();

    public PersonRequestDto() {

    }
    public PersonRequestDto(String email, String password, String name, Museum museum) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.museum = museum;
    }

    public Person toModel() {
        Person person = new Person(email,password,name,museum);
        for(Integer e: personRoleIds) {
            try{
                Employee employee = employeeService.getEmployeeById(e);
                person.addPersonRole(employee);
            } catch (MuseumBackendException exception) {}
            try {
                Customer customer = customerService.getCustomerById(e);
                person.addPersonRole(customer);
            } catch (MuseumBackendException exception) {}
            try {
                Admin admin = adminService.getAdminById(e);
                person.addPersonRole(admin);
            } catch (MuseumBackendException exception) {}
        }
        return person;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public List<Integer> getPersonRoleIds() {
        return personRoleIds;
    }

    public void setPersonRoleIds(List<Integer> personRoleIds) {
        this.personRoleIds = personRoleIds;
    }
}

package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.PersonRole;

import java.util.ArrayList;
import java.util.List;

public class PersonResponseDto {

    private String email;
    private String password;
    private String name;
    private Museum museum;
    private List<Integer> personRoleIds = new ArrayList<>();

    public PersonResponseDto() {
    }
    public PersonResponseDto(String email, String password, String name, Museum museum, List<PersonRole> personRoles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.museum = museum;
        for (PersonRole e: personRoles) {
            personRoleIds.add(e.getPersonRoleId());
        }
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

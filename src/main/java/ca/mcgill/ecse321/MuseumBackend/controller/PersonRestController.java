package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.PersonRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.PersonResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class PersonRestController {
    @Autowired
    private PersonService service;

    @GetMapping(value = "/person/{email}")
    public ResponseEntity<PersonResponseDto> getPersonByEmail(@PathVariable String email) throws IllegalArgumentException {
        return new ResponseEntity<>(convertToResponseDto(service.getPersonByEmail(email)), HttpStatus.OK);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonRequestDto personRequestDto) throws IllegalArgumentException {
        return new ResponseEntity<>(convertToResponseDto(service.createPerson(personRequestDto.toModel())),HttpStatus.CREATED);
    }
    @PutMapping(value = "/person/{email}/")
    public void changePersonNameAndPassword(@PathVariable String email, @RequestBody Map<String, String> inputMap) throws IllegalArgumentException {
        String name = inputMap.get("name");
        String password = inputMap.get("password");
        if(service.getPersonByEmail(email).getName() != name)service.changePersonName(email,name);
        if(service.getPersonByEmail(email).getPassword() != password)service.changePersonPassword(email,password);
    }

    @PutMapping(value = "/person/{email}")
    public void deletePerson(@PathVariable String email) throws  IllegalArgumentException {
        service.deletePerson(email);
    }

    private PersonResponseDto convertToResponseDto(Person p) {
        if(p==null)throw new IllegalArgumentException("There is no such Person!");
        return new PersonResponseDto(p.getEmail(),p.getPassword(),p.getName(),p.getMuseum(),p.getPersonRoles());
    }
}

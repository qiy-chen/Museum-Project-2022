package ca.mcgill.ecse321.MuseumBackend.service;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestPersonService {
    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    private Person initializeTestPerson() {
        Museum museum = new Museum();
        museum.setMuseumId(5);
        String email = "sfaubert9@gmail.com";
        String password = "yoyo";
        String name = "Samuel Faubert";
        return new Person(email,password,name,museum);
    }

    @Test
    public void testCreatePerson() {
        Person person0 = initializeTestPerson();
        when(personRepository.save(person0)).thenAnswer((InvocationOnMock invocation) -> person0);
        Person person = personService.createPerson(person0);
        assertEquals(person0.getEmail(), person.getEmail());
    }
    @Test
    public void testFindPersonByEmail() {
        Person person0 = initializeTestPerson();
        when(personRepository.findPersonByEmail(person0.getEmail())).thenAnswer((InvocationOnMock invocation) -> person0);
        assertEquals(person0.getEmail(), personService.getPersonByEmail(person0.getEmail()).getEmail());
    }
    @Test
    public void testChangePersonPassword() {
        Person person0 = initializeTestPerson();
        String oldPassword = person0.getPassword();
        String newPassword = "yoyoyo";
        when(personRepository.findPersonByEmail(person0.getEmail())).thenAnswer((InvocationOnMock invocation) -> person0);
        Person person = personService.changePersonPassword(person0.getEmail(),newPassword);
        assertEquals(person0.getEmail(),person.getEmail());
        assertEquals(newPassword,person.getPassword());
        assertNotEquals(oldPassword,person.getPassword());
    }
    @Test
    public void testChangePersonName() {
        Person person0 = initializeTestPerson();
        String oldName = person0.getName();
        String newName = "Marwaan";
        when(personRepository.findPersonByEmail(person0.getEmail())).thenAnswer((InvocationOnMock invocation) -> person0);
        Person person = personService.changePersonName(person0.getEmail(),newName);
        assertEquals(person0.getEmail(),person.getEmail());
        assertEquals(newName,person.getName());
        assertNotEquals(oldName,person.getName());
    }
}

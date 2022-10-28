package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
@Author  Samuel Faubert
 */
@SpringBootTest
public class PersonRepositoryTests {
    @Autowired
    private PersonRepository personRepository;


    @AfterEach
    public void clearDatabase() {
        personRepository.deleteAll();
    }

    @Test
    public void testPersonPersistAndLoad() {
        // Create object
        String email = "samuel.faubert@mail.mcgill.ca";
        String name = "Samuel Faubert";
        String password = "MarwanisC00l";
        Person samuel = new Person();
        samuel.setEmail(email);
        samuel.setName(name);
        samuel.setPassword(password);
        // Save object
        samuel = personRepository.save(samuel);
        String idEmail = samuel.getEmail();
        samuel = null;
        // Read object from database
        samuel = personRepository.findPersonByEmail(email);
        assertNotNull(samuel);
        assertEquals(idEmail, samuel.getEmail());
        assertEquals(name, samuel.getName());
        assertEquals(password, samuel.getPassword());

    }
}

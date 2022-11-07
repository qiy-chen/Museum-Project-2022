package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        String email = "samuel.faubert@mail.mcgill.ca";
        String name = "Samuel Faubert";
        String password = "MarwanisC00l";
        Person samuel = new Person();
        samuel.setEmail(email);
        samuel.setName(name);
        samuel.setPassword(password);
        samuel = personRepository.save(samuel);
        String idEmail = samuel.getEmail();
        samuel = null;
        samuel = personRepository.findPersonByEmail(email);
        assertNotNull(samuel);
        assertEquals(idEmail, samuel.getEmail());

    }
}

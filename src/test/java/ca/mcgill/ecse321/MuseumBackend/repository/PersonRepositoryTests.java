package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
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

    @Autowired
    private MuseumRepository museumRepository;

    @Autowired
    private AdminRepository adminRepository;


    @AfterEach
    public void clearDatabase() {
        adminRepository.deleteAll();
        personRepository.deleteAll();
        museumRepository.deleteAll();
    }

    @Test
    public void testPersonPersistAndLoad() {
        // Create object
        Museum museum = new Museum(12);
        museum = museumRepository.save(museum);
        String email = "samuel.faubert@mail.mcgill.ca";
        String name = "Samuel Faubert";
        String password = "MarwanisC00l";
        Person samuel = new Person();
        samuel.setEmail(email);
        samuel.setName(name);
        samuel.setPassword(password);
        samuel.setMuseum(museum);
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
    @Test
    public void testPersonAssociations() {
        Museum museum = new Museum(12);
        museum = museumRepository.save(museum);
        Admin admin = new Admin();
        admin.setPersonRoleId(23);
        admin = adminRepository.save(admin);
        String email = "samuel.faubert@mail.mcgill.ca";
        String name = "Samuel Faubert";
        String password = "MarwanisC00l";
        Person samuel = new Person();
        samuel.setEmail(email);
        samuel.setName(name);
        samuel.setPassword(password);
        samuel.setMuseum(museum);
        admin.setPerson(samuel);
        samuel = personRepository.save(samuel);
        String idEmail = samuel.getEmail();
        int adminId = admin.getPersonRoleId();
        int museumId = museum.getMuseumId();
        samuel = null;
        admin = null;
        museum = null;
        samuel = personRepository.findPersonByEmail(idEmail);
        admin = adminRepository.findAdminByPersonRoleId(adminId);
        museum = museumRepository.findMuseumByMuseumId(museumId);
        assertNotNull(samuel);
        assertNotNull(admin);
        assertNotNull(museum);
        assertEquals(samuel.getEmail(),museum.getPerson(0).getEmail());
        assertEquals(samuel.getEmail(), admin.getPerson().getEmail());

    }
}

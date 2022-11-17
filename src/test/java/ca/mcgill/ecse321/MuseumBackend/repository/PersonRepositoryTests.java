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
    private AdminRepository adminRepository;


    @AfterEach
    public void clearDatabase() {
        personRepository.deleteAll();
        adminRepository.deleteAll();
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
        
        // check values
        assertNotNull(samuel);
        assertEquals(idEmail, samuel.getEmail());
        assertEquals(name, samuel.getName());
        assertEquals(password, samuel.getPassword());

    }
    @Test
    public void testPersonAssociations() {
        
    	Admin admin = new Admin();
        admin = adminRepository.save(admin);
        int adminId = admin.getPersonRoleId();
        
        String email = "samuel.faubert@mail.mcgill.ca";
        String name = "Samuel Faubert";
        String password = "MarwanisC00l";
        Person samuel = new Person();
        samuel.setEmail(email);
        samuel.setName(name);
        samuel.setPassword(password);
        admin.setPerson(samuel);
        samuel = personRepository.save(samuel);
        admin = adminRepository.save(admin);
        String idEmail = samuel.getEmail();
        
        samuel = null;
        admin = null;
        samuel = personRepository.findPersonByEmail(idEmail);
        admin = adminRepository.findAdminByPersonRoleId(adminId);
        
        // check values
        assertNotNull(samuel);
        assertNotNull(admin);
        assertEquals(samuel.getEmail(), admin.getPerson().getEmail());

    }
}

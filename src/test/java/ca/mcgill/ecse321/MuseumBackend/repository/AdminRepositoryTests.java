package ca.mcgill.ecse321.MuseumBackend.repository;

import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Person;



@SpringBootTest
public class AdminRepositoryTests {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    public void clearDatabase() {
      adminRepository.deleteAll();
        personRepository.deleteAll();
        
    }
    
    @Test
    public void testPersistAndLoadAdmin() {
        // Create object
        Admin aAdmin = new Admin();

        // Save object
        aAdmin = adminRepository.save(aAdmin);
        int adminId = aAdmin.getPersonRoleId();

        // Read object from database
        aAdmin = null;
        aAdmin = adminRepository.findAdminByPersonRoleId(adminId);

        // Assert that object has correct attributes
        assertNotNull(aAdmin);
        assertEquals(adminId, aAdmin.getPersonRoleId());
    }
    
    @Test
    public void testAdminToPersonReference() {
      
        // Create person object

        String email = "sandy@hotmail.com";
        Person sandy = new Person();
        sandy.setEmail(email);
        sandy = personRepository.save(sandy);
        
        //Create Admin
        Admin aAdmin = new Admin();
        aAdmin.setPerson(sandy);

        // Update object
        aAdmin = adminRepository.save(aAdmin);
        int adminID = aAdmin.getPersonRoleId();

        // Read object from database
        aAdmin = adminRepository.findAdminByPersonRoleId(adminID);

        // Assert that object has correct attributes
        assertNotNull(aAdmin);
        assertEquals(adminID, aAdmin.getPersonRoleId());
        assertEquals(email,aAdmin.getPerson().getEmail());
    }
    
    
}
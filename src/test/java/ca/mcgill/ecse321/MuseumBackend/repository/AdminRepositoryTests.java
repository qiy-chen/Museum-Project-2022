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
    @Autowired
    private MuseumRepository museumRepository;

    @AfterEach
    public void clearDatabase() {
        adminRepository.deleteAll();
        personRepository.deleteAll();
        museumRepository.deleteAll();
    }
    
    @Test
    public void testPersistAndLoadAdmin() {
        // Create object
        Admin aAdmin = new Admin();

        // Save object
        adminRepository.save(aAdmin);
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
      
        // Create object
        Admin aAdmin = new Admin();
        adminRepository.save(aAdmin); // save before adding art so that it is present for the foreign key when saving the artwork
        
        // create references
        Museum aMuseum = new Museum(21);
        museumRepository.save(aMuseum);
        
        String email = "sandy@hotmail.com";
        Person sandy = new Person();
        sandy.addPersonRole(aAdmin);
        sandy.setMuseum(aMuseum);
        sandy.setEmail(email);
        personRepository.save(sandy);
        aAdmin.setPerson(sandy);

        // Update object
        adminRepository.save(aAdmin);
        int adminID = aAdmin.getPersonRoleId();

        // Read object from database
        aAdmin = adminRepository.findAdminByPersonRoleId(adminID);

        // Assert that object has correct attributes
        assertNotNull(aAdmin);
        assertEquals(adminID, aAdmin.getPersonRoleId());
        assertEquals(email,aAdmin.getPerson().getEmail());
    }
    
    
}
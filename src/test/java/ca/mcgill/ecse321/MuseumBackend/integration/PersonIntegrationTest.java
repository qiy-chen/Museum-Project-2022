package ca.mcgill.ecse321.MuseumBackend.integration;

import ca.mcgill.ecse321.MuseumBackend.dto.*;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.repository.MuseumRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonIntegrationTest {
    private String email = "samuel.faubert@mail.mcgill.ca";
    private String password = "yoyo";
    private String name = "Samuel Faubert";
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private MuseumRepository museumRepository;
    @Autowired
    private PersonRepository personRepository;
    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        personRepository.deleteAll();
        museumRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetPerson() {
        String email = testCreatePerson();
        testGetPerson(email);
    }

    private String testCreatePerson() {
        Museum museum = new Museum(12);
        museum = museumRepository.save(museum);
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/person", new PersonRequestDto(email,password,name,museum.getMuseumId()),PersonResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(this.email, response.getBody().getEmail(),"Response has correct id");
        return response.getBody().getEmail();
    }

    private void testGetPerson(String email) {
        ResponseEntity<PersonResponseDto> response = client.getForEntity("/person/"+email,PersonResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(this.email, response.getBody().getEmail(), "Response has correct id");
    }

    @Test
    public void testCreateAndGetPersonAndChangePassword() {
        String email = testCreatePerson();
        testGetPerson(email);
        testChangePassword(email, "yoyoyo4");

    }

    private void testChangePassword(String email, String newPassword) {
        ResponseEntity<PersonResponseDto> response0 = client.getForEntity("/person/"+email,PersonResponseDto.class);
        String oldPassword = response0.getBody().getPassword();
        String name = response0.getBody().getName();
        Map<String,String> inputMap = new HashMap<>();
        inputMap.put("name",name);
        inputMap.put("password",newPassword);
        client.put("/person/"+email+"/",inputMap);
        ResponseEntity<PersonResponseDto> response = client.getForEntity("/person/"+email,PersonResponseDto.class);
        assertEquals(newPassword, response.getBody().getPassword());
        assertEquals(name,response.getBody().getName());
        assertNotEquals(response0.getBody().getPassword(),response.getBody().getPassword());
        assertEquals(response0.getBody().getName(),response.getBody().getName());
    }

    @Test
    public void testCreateAndGetPersonAndChangeName() {
        String email = testCreatePerson();
        testGetPerson(email);
        testChangeName(email,"Marwan");

    }
    private void testChangeName(String email, String newName) {
        ResponseEntity<PersonResponseDto> response0 = client.getForEntity("/person/"+email,PersonResponseDto.class);
        String password = response0.getBody().getPassword();
        String oldName = response0.getBody().getName();
        Map<String,String> inputMap = new HashMap<>();
        inputMap.put("name",newName);
        inputMap.put("password",password);
        client.put("/person/"+email+"/",inputMap);
        ResponseEntity<PersonResponseDto> response = client.getForEntity("/person/"+email,PersonResponseDto.class);
        assertEquals(newName, response.getBody().getName());
        assertEquals(password,response.getBody().getPassword());
        assertNotEquals(response0.getBody().getName(),response.getBody().getName());
        assertEquals(response0.getBody().getPassword(),response.getBody().getPassword());
    }

    @Test
    public void testCreateGetAndDeletePerson() {
        String email = testCreatePerson();
        testGetPerson(email);
        testDeletePerson(email);
    }
    private void testDeletePerson(String email) {
        client.put("/person/"+email,null);
        try {
            client.getForEntity("/person/"+email,PersonResponseDto.class);
            fail("Person was found!");
        }catch (RestClientException|IllegalArgumentException e) {

        }
    }
    @Test
    public void testCreateAndGetPersonAndCreateAndGetPersonRoles() {
        String email = testCreatePerson();
        testCreateGiveAndGetPersonRoles(email);

    }
    private void testCreateGiveAndGetPersonRoles(String email) {
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setEmail(email);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setEmail(email);
        ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customer",customerRequestDto,CustomerResponseDto.class);
        ResponseEntity<EmployeeResponseDto> employeeResponse = client.postForEntity("/employee",employeeRequestDto,EmployeeResponseDto.class);
        ResponseEntity<Integer[]> personRolesResponse = client.getForEntity("/person/person_roles/"+email,Integer[].class);
        assertTrue(Arrays.stream(personRolesResponse.getBody()).toList().contains(employeeResponse.getBody().getId()));
        assertTrue(Arrays.stream(personRolesResponse.getBody()).toList().contains(customerResponse.getBody().getId()));
    }

}

package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.AdminRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.AdminResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.AdminRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class TestAdminService {

	// Replace the repository with a "mock" that exposes the same interface
	@Mock
	AdminRepository adminRepo;
	@Mock
	PersonRepository personRepo;

	// Get a service that uses the mock repository
	@InjectMocks
	AdminService adminService;
	@InjectMocks
	AdminService personService;

	@Test
	public void testGetAdminById() {
		// Tell the mocked repository how to behave
		final int id = 1;
		final Admin hannah = new Admin();
		hannah.setPersonRoleId(id);
		when(adminRepo.findAdminByPersonRoleId(id)).thenAnswer((InvocationOnMock invocation) -> hannah);

		// Test that the service behaves properly
		Admin admin = adminService.getAdminById(id);

		assertNotNull(admin);
		assertEquals(id, admin.getPersonRoleId());
	}

	@Test
	public void testGetAdminByInvalidId() {
		final int invalidId = 99;

		// Mock: if asking for a admin with invalid ID, return null
		when(adminRepo.findAdminByPersonRoleId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

		// call method, and obtain resulting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> adminService.getAdminById(invalidId));

		// check results
		assertEquals("Admin not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}

	@Test
	public void testCreateAdmin() {
		// Mock: just return the Admin with no modification
		when(adminRepo.save(any(Admin.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

		// test set up - create a person
		Person person = new Person();
		String email = "finnigan@mail.com";
		String name = "Finn Igan";
		person.setEmail(email);
		person.setName(name);
		// when searching for person using email, return person
		when(personRepo.findPersonByEmail(email)).thenAnswer((InvocationOnMock invocation) -> person);
		// create the admin request and tie it to the existing person
		AdminRequestDto finn = new AdminRequestDto();
		finn.setEmail(email);

		// call method
		AdminResponseDto returnedAdmin = adminService.createAdmin(email);

		// check results
		assertNotNull(returnedAdmin);
		assertEquals(email, returnedAdmin.getPerson().getEmail());
		// Check that the service actually saved the admin
		// verify(adminRepo, times(1)).save();
	}

	// check if creating admin given person who is already an admin throws exception
	@Test
	public void testInvalidCreateExistingAdmin() {

		// test set up - create a person who is already an admin
		Person person = new Person();
		String email = "finnigan@mail.com";
		String name = "Finn Igan";
		person.setEmail(email);
		person.setName(name);
		Admin existingAdmin = new Admin();
		person.addPersonRole(existingAdmin);
		// when searching for person using email, return person
		when(personRepo.findPersonByEmail(email)).thenAnswer((InvocationOnMock invocation) -> person);
		// create the admin request and tie it to the existing person
		// AdminRequestDto invalidRequest = new AdminRequestDto();
		// invalidRequest.setEmail(email);

		// call method expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class, () -> adminService.createAdmin(email));

		// check results
		assertEquals(ex.getMessage(), "Person with given email is already an admin.");
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
	}

	// check if creating admin given person who does not yet exist throws exception
	@Test
	public void testInvalidCreateAdminForMissingPerson() {

		String email = "finnigan@mail.com";
		// when searching for a person using an email, return null since the person does
		// not exist
		when(personRepo.findPersonByEmail(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null);
		// create the admin request and tie it to the existing person
		AdminRequestDto invalidRequest = new AdminRequestDto();
		invalidRequest.setEmail(email);

		// call method expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class, () -> adminService.createAdmin(email));

		// check results
		assertEquals(ex.getMessage(), "Person not found.");
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}

	// test delete admin
	@Test
	public void testDeleteAdmin() {
		// setup: create an admin to delete
		Person person = new Person();
		String email = "finnigan@mail.com";
		String name = "Finn Igan";
		person.setEmail(email);
		person.setName(name);
		Admin smith = new Admin();
		int id = smith.getPersonRoleId();
		smith.setPerson(person);

		// Mock: if looking for smith using their ID, return smith
		when(adminRepo.findAdminByPersonRoleId(id)).thenAnswer(x -> smith);

		// call method
		adminService.deleteAdmin(id);

		// check results
		verify(adminRepo, times(1)).delete(smith);
	}

	// test invalid delete admin - they are not found
	@Test
	public void testInvalidDeleteAdmin() {
		// Mock: if searching using an invalid admin id return null
		when(adminRepo.findAdminByPersonRoleId(any(int.class))).thenAnswer(x -> null);

		// call method, expecting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class,
				() -> adminService.deleteAdmin(Integer.MAX_VALUE));

		// check results
		assertEquals(ex.getMessage(), "Admin not found.");
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}

}

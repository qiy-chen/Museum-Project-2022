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
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.repository.AdminRepository;

@ExtendWith(MockitoExtension.class)
public class TestAdminService {

	// Replace the repository with a "mock" that exposes the same interface
	@Mock
	AdminRepository adminRepo;
	
	// Get a service that uses the mock repository
	@InjectMocks
	AdminService adminService;
	
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
		MuseumBackendException ex = assertThrows(MuseumBackendException.class, () -> adminService.getAdminById(invalidId));
		
		// check results
		assertEquals("Admin not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	@Test
	public void testCreateAdmin() {
		// Mock: just return the Admin with no modification
		when(adminRepo.save(any(Admin.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		// test set up
		final Admin finn = new Admin();
		int id = finn.getPersonRoleId();
		
		// call method
		Admin returnedAdmin = adminService.createAdmin(finn);
		
		// check results
		assertNotNull(returnedAdmin);
		assertEquals(id, returnedAdmin.getPersonRoleId());
		// Check that the service actually saved the admin
		verify(adminRepo, times(1)).save(finn);
	}
	
}

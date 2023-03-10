package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.AdminResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.AdminRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jeanine Looman
 */
@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepo;
	@Autowired
	PersonRepository personRepo;
	
	// get Admin by ID
	@Transactional
	public Admin getAdminById(int id) {
		Admin admin = adminRepo.findAdminByPersonRoleId(id);
		if (admin == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Admin not found.");
		}
		return admin;
	}
	@Transactional
	public List<Admin> getAdmins() {
		List<Admin> admins = new ArrayList<>();
		Iterable<Admin> adminsIterable = adminRepo.findAll();
		for(Admin admin:adminsIterable) {
			admins.add(admin);
		}
		return admins;
	}
	
	// create Admin given the email of the person to give that role
	@Transactional
	public AdminResponseDto createAdmin(String email) {
		// check if the person with the given email already exists, else throw error (we don't want to create people from the role end)
		Person person = personRepo.findPersonByEmail(email);
		if (person == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Person not found.");
		}
		if (person.isAdmin()) {
			throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Person with given email is already an admin.");
		}
		Admin admin = new Admin();
		admin.setPerson(person);
		admin = adminRepo.save(admin);
		return new AdminResponseDto(admin);
	}
	
	// remove admin from database
		@Transactional
		public void deleteAdmin(int ID) {
			// find admin if they exist
			Admin admin = adminRepo.findAdminByPersonRoleId(ID);
			if (admin == null)
				throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Admin not found.");
			adminRepo.delete(admin);
			admin.delete();
		}
}

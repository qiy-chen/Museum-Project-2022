package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.AdminRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.AdminResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.repository.AdminRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepo;
	@Autowired
	PersonService personService;
	
	@Transactional
	public Admin getAdminById(int id) {
		Admin admin = adminRepo.findAdminByPersonRoleId(id);
		if (admin == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Admin not found.");
		}
		return admin;
	}
	
	// create Admin 
	@Transactional
	public AdminResponseDto createAdmin(AdminRequestDto adminDto) {
		Person person = personService.getPersonByEmail(adminDto.getPersonEmail());
		Admin admin = new Admin();
		admin.setPerson(person);
		admin = adminRepo.save(admin);
		return new AdminResponseDto(admin);
	}
}

package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepo;
	
	@Transactional
	public Admin getAdminById(int id) {
		Admin admin = adminRepo.findAdminByPersonRoleId(id);
		if (admin == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Admin not found.");
		}
		return admin;
	}
	
	@Transactional
	public Admin createAdmin(Admin admin) {
		admin = adminRepo.save(admin);
		return admin;
	}
}

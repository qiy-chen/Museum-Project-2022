package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.AdminRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.AdminResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ShiftResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Jeanine Looman
 */
@CrossOrigin(origins = "*")
@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	// find admin by their ID
	@GetMapping("/admin/{id}")
	public ResponseEntity<AdminResponseDto> getAdminByID(@PathVariable int id) {

		Admin admin = adminService.getAdminById(id);
		return new ResponseEntity<AdminResponseDto>(new AdminResponseDto(admin),
				HttpStatus.OK);
	}

	// create admin
	@PostMapping("/admin")
	public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminRequestDto request) {
		AdminResponseDto response = adminService.createAdmin(request.getPersonEmail());
		return new ResponseEntity<AdminResponseDto>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = "/admin")
	public ResponseEntity<AdminResponseDto[]> getAdmin() throws IllegalArgumentException {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		List<Admin> admins = adminService.getAdmins();
		AdminResponseDto[] adminResponses = new AdminResponseDto[admins.size()];
		int i = 0;
		for(Admin admin:admins) {
			adminResponses[i] = new AdminResponseDto(admin);
			i++;
		}
		return new ResponseEntity<>(adminResponses,httpHeaders,HttpStatus.OK);
	}

	// delete admin by ID
	@DeleteMapping("/admin/{id}")
	public void deleteAdmin(@PathVariable int id) {
		adminService.deleteAdmin(id);
	}
}

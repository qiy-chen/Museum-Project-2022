package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.AdminRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.AdminResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	// find admin by their ID
	@GetMapping("/admin/{id}")
	public ResponseEntity<AdminResponseDto> getAdminByID(@PathVariable int id) {
		Admin admin = adminService.getAdminById(id);
		return new ResponseEntity<AdminResponseDto>(new AdminResponseDto(admin), HttpStatus.OK);
	}

	// create admin
	@PostMapping("/admin")
	public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminRequestDto request) {
		AdminResponseDto response = adminService.createAdmin(request.getPersonEmail());
		return new ResponseEntity<AdminResponseDto>(response, HttpStatus.CREATED);
	}

	// delete admin by ID
	@DeleteMapping("/admin/{id}")
	public void deleteAdmin(@PathVariable int id) {
		adminService.deleteAdmin(id);
	}
}

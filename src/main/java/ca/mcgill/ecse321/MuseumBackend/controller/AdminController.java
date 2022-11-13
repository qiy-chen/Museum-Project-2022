package ca.mcgill.ecse321.MuseumBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.MuseumBackend.dto.AdminRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.AdminResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;
import ca.mcgill.ecse321.MuseumBackend.service.AdminService;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;
	
	// find admin by their ID
	@GetMapping("/admin/{id}")
	public ResponseEntity<AdminResponseDto> getAdminByID(@PathVariable int ID) {
		Admin admin = adminService.getAdminById(ID);
		return new ResponseEntity<AdminResponseDto>(new AdminResponseDto(admin), HttpStatus.OK);
	}
	
	// create admin
	@PostMapping("/admin")
	public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminRequestDto request) {
		Admin adminToCreate = request.toModel();
		Admin createdAdmin = adminService.createAdmin(adminToCreate);
		AdminResponseDto response = new AdminResponseDto(createdAdmin);
		return new ResponseEntity<AdminResponseDto>(response, HttpStatus.CREATED);
	}
}

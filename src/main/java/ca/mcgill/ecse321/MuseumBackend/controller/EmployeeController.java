package ca.mcgill.ecse321.MuseumBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/employee/{name}")
	public ResponseEntity<EmployeeResponseDto> getEmployeeByID(@PathVariable int ID) {
		Employee employee = employeeService.getEmployeeById(ID);
		return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employee), HttpStatus.OK);
	}
	
	@PostMapping("/employee")
	public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto request) {
		Employee employeeToCreate = request.toModel();
		Employee createdEmployee = employeeService.createEmployee(employeeToCreate);
		EmployeeResponseDto response = new EmployeeResponseDto(createdEmployee);
		return new ResponseEntity<EmployeeResponseDto>(response, HttpStatus.CREATED);
	}
}

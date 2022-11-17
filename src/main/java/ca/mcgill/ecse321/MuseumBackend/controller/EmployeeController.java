package ca.mcgill.ecse321.MuseumBackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	// find employee by employee ID
	@GetMapping("/employee/{id}")
	public ResponseEntity<EmployeeResponseDto> getEmployeeByID(@PathVariable int id) {
		Employee employee = employeeService.getEmployeeById(id);
		return new ResponseEntity<EmployeeResponseDto>(new EmployeeResponseDto(employee), HttpStatus.OK);
	}
	
	// create new employee
	@PostMapping("/employee")
	public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto request) {
		EmployeeResponseDto response = employeeService.createEmployee(request.getEmail());
		return new ResponseEntity<EmployeeResponseDto>(response, HttpStatus.CREATED);
	}
	
	// fire employee by ID
	@DeleteMapping("/employee/fire/{id}")
	public ResponseEntity<EmployeeResponseDto> fireEmployee(@PathVariable int id) {
		EmployeeResponseDto response = new EmployeeResponseDto(employeeService.fireEmployee(id));
		return new ResponseEntity<EmployeeResponseDto>(response, HttpStatus.OK);
	}
	
	// get all employees
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		ArrayList<EmployeeResponseDto> employeeDtos = new ArrayList<>();
		for (Employee e : employees)
			employeeDtos.add(new EmployeeResponseDto(e));
		return new ResponseEntity<List<EmployeeResponseDto>>(toList(employeeDtos), HttpStatus.OK);
	}
	
	// convert ArrayList to List
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}

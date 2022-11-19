package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ShiftResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jeanine Looman
 */
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
	@DeleteMapping("/employee/{id}")
	public void fireEmployee(@PathVariable int id) {
		employeeService.fireEmployee(id);
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
	
	// get all shifts for employee
	@GetMapping("/employee/shifts/{id}")
	public ResponseEntity<List<ShiftResponseDto>> getShiftsForEmployee(@PathVariable int id) {
		List<Shift> shifts = employeeService.getShiftsForEmployee(id);
		ArrayList<ShiftResponseDto> shiftDtos = new ArrayList<>();
		for (Shift s : shifts)
			shiftDtos.add(new ShiftResponseDto(s));
		return new ResponseEntity<List<ShiftResponseDto>>(toList(shiftDtos), HttpStatus.OK);
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

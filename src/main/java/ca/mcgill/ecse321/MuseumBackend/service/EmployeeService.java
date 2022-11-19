package ca.mcgill.ecse321.MuseumBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.PersonRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.ShiftRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepo;
	@Autowired
	PersonRepository personRepo;
	@Autowired
	ShiftRepository shiftRepo;

	// find one employee by their ID
	@Transactional
	public Employee getEmployeeById(int id) {
		Employee employee = employeeRepo.findEmployeeByPersonRoleId(id);
		if (employee == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Employee not found.");
		}
		return employee;
	}

	// create a new employee
	@Transactional
	public EmployeeResponseDto createEmployee(String email) {
		// check if the person with the given email already exists, else throw error (we
		// don't want to create people from the role end)
		Person person = personRepo.findPersonByEmail(email);
		if (person == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Person not found.");
		}
		if (person.isEmployee()) {
			throw new MuseumBackendException(HttpStatus.BAD_REQUEST, "Person with given email is already an employee.");
		}
		Employee employee = new Employee();
		employee.setPerson(person);
		employee = employeeRepo.save(employee);
		return new EmployeeResponseDto(employee);
	}

	// find all employees
	@Transactional
	public List<Employee> getAllEmployees() {
		return toList(employeeRepo.findAll());
	}
	
	// find all shifts for one employee
	@Transactional
	public List<Shift> getShiftsForEmployee(int id) {
		Employee employee = employeeRepo.findEmployeeByPersonRoleId(id);
		if (employee == null)
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Employee not Found");
		return employee.getShifts();
	}

	// remove employee from database
	@Transactional
	public void fireEmployee(int ID) {
		// find employee if they exist
		Employee employee = employeeRepo.findEmployeeByPersonRoleId(ID);
		if (employee == null)
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Employee not found.");
		employeeRepo.delete(employee);
		employee.delete();
	}

	// convert ArrayList to List
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}

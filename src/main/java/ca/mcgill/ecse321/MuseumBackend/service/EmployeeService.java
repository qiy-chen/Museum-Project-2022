package ca.mcgill.ecse321.MuseumBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepo;
	
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
	public Employee createEmployee(Employee employee) {
		employee = employeeRepo.save(employee);
		return employee;
	}
	
	// find all employees
	@Transactional
	public List<Employee> getAllEmployees() {
		return toList(employeeRepo.findAll());
	}
	
	// remove employee from database
	@Transactional
    public Employee fireEmployee(int ID){
        Employee employee = employeeRepo.findEmployeeByPersonRoleId(ID);
        if(employee == null) 
        	throw new NullPointerException("Employee not found");
        employeeRepo.delete(employee); 
        return employee;
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

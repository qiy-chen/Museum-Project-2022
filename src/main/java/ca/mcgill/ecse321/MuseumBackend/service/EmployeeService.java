package ca.mcgill.ecse321.MuseumBackend.service;

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
	
	@Transactional
	public Employee getEmployeeById(int id) {
		Employee employee = employeeRepo.findEmployeeByPersonRoleId(id);
		if (employee == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Employee not found.");
		}
		return employee;
	}
	
	@Transactional
	public Employee createEmployee(Employee employee) {
		employee = employeeRepo.save(employee);
		return employee;
	}
}

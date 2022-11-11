package ca.mcgill.ecse321.MuseumBackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
	Employee findEmployeeByPersonRoleId(int aUserRoleId);
	List<Employee> findAll();
	
}

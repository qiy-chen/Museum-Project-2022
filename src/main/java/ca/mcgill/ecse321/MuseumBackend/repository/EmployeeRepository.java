package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>{
	
	Employee findEmployeeByAUserRoleId(String aUserRoleId);
	
}

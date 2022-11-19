package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
	Employee findEmployeeByPersonRoleId(int aUserRoleId);
	
}

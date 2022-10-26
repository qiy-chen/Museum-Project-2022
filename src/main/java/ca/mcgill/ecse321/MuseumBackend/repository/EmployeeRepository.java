package ca.mcgill.ecse321.MuseumBackend.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, UUID>{
	
	Employee findEmployeeByAUserRoleId(UUID aUserRoleId);
	
}

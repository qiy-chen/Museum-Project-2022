package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer>{

	Admin findAdminByPersonRoleId(int aPersonRoleId);
	
}

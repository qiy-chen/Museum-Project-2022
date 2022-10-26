package ca.mcgill.ecse321.MuseumBackend.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Admin;

public interface AdminRepository extends CrudRepository<Admin, UUID>{

	Admin findAdminByAUserRoleId(UUID aUserRoleId);
	
}

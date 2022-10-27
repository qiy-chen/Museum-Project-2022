package ca.mcgill.ecse321.MuseumBackend.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

	Customer findCustomerByPersonRoleId(int aCustomerId);
	
}
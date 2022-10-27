package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

public interface ShiftRepository extends CrudRepository<Shift, Integer>{

	Shift findShiftByShiftId(int aShiftId);
	
}
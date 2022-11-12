package ca.mcgill.ecse321.MuseumBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.repository.ShiftRepository;

@Service
public class ShiftService {

	@Autowired
	ShiftRepository shiftRepo;
	
	@Transactional
	public Shift getShiftById(int id) {
		Shift shift = shiftRepo.findShiftByWorkDayId(id);
		if (shift == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Shift not found.");
		}
		return shift;
	}
	
	@Transactional
	public Shift createShift(Shift shift) {
		shift = shiftRepo.save(shift);
		return shift;
	}


}

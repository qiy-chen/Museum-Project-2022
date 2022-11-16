package ca.mcgill.ecse321.MuseumBackend.service;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.repository.ShiftRepository;

import java.sql.Date;

@Service
public class ShiftService {

	@Autowired
	ShiftRepository shiftRepo;

	private void shiftArgumentErrorTest(Shift shift) {
		String error = "";
		if (shift == null) {
			throw new IllegalArgumentException("Shift cannot be null!");
		}
		if(shift.getStartTime() == null) {
			error += "startTime cannot be null!";
		}
		if(shift.getEndTime() == null) {
			error += "endTime cannot be null!";
		}
		if(shift.getMuseum() == null) {
			error += "museum cannot be null!";
		}
		error = error.trim();
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
	}

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
		shiftArgumentErrorTest(shift);
		return shift;
	}

	@Transactional
	public Shift changeShiftDate(int workDayId, Date startTime, Date endTime) {
		Shift shift = getShiftById(workDayId);
		shift.setStartTime(startTime);
		shift.setEndTime(endTime);
		shiftArgumentErrorTest(shift);
		return shift;
	}

	@Transactional
	public Shift addEmployeeToShift(int workDayId, Employee employee) {
		Shift shift = getShiftById(workDayId);
		shift.addEmployee(employee);
		shiftArgumentErrorTest(shift);
		return shift;
	}

	@Transactional
	public Shift removeEmployeeFromShift(int workDayId, Employee employee) {
		Shift shift = getShiftById(workDayId);
		shift.removeEmployee(employee);
		shiftArgumentErrorTest(shift);
		return shift;
	}
	@Transactional
	public void deleteShift(int workDayId) {
		Shift shift = getShiftById(workDayId);
		shiftRepo.delete(shift);
		shift.delete();
	}



}

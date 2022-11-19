package ca.mcgill.ecse321.MuseumBackend.service;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.repository.ShiftRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class ShiftService {

	@Autowired
	ShiftRepository shiftRepo;
	@Autowired
	EmployeeRepository employeeRepository;

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
	public Shift getShiftById(int workDayid) {
		/**
		 * Takes an identifying integer to find its corresponding shift in the shift repository
		 * @param workDayId An integer equal to the unique value corresponding to a shift
		 * @return shift A Shift object from the shift repository that corresponds to the id
		 */
		Shift shift = shiftRepo.findShiftByWorkDayId(workDayid);
		if (shift == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Shift not found.");
		}
		return shift;
	}
	
	@Transactional
	public Shift createShift(Shift shift) {
		/**
		 * Takes a Shift object to be saved to the shift repository
		 * @param shift A Shift object to be persisted
		 * @return shift The same Shift object after it has been persisted
		 */
		shift = shiftRepo.save(shift);
		shiftArgumentErrorTest(shift);
		return shift;
	}

	@Transactional
	public Shift changeShiftDate(int workDayId, String startTimeValue, String endTimeValue) {
		/**
		 * Takes an identifying integer and 2 Date objects to find its corresponding shift in the shift repository to change the start and end times of the shift
		 *  @param workDayId An integer equal to the unique value corresponding to a shift
		 *	@param startTime A Date object equal to the new starting time to be set for the shift
		 * 	@param endTime A Date object equal to the new ending time to be set for the shift
		 * 	@return shift The Shift object corresponding to the workDayId with its start and end times now updated
		 */
		Shift shift = getShiftById(workDayId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
		shift.setStartTime(LocalDateTime.parse(startTimeValue,formatter));
		shift.setEndTime(LocalDateTime.parse(endTimeValue,formatter));
		shiftArgumentErrorTest(shift);
		return shift;
	}

	@Transactional
	public Shift addEmployeeToShift(int workDayId, int employeeId) {
		/**
		 * Takes 2 identifying integers to find the corresponding shift in the shift repository to add the corresponding employee to the shift
		 * @param workDayId An integer equal to the unique value corresponding to a shift
		 * @param employeeId An integer equal to the unique value corresponding to an employee
		 * @return shift The Shift object corresponding to the workDayId with the requested employee added
		 */
		Shift shift = getShiftById(workDayId);
		Employee employee = employeeRepository.findEmployeeByPersonRoleId(employeeId);
		shift.addEmployee(employee);
		shiftArgumentErrorTest(shift);
		return shift;
	}

	@Transactional
	public Shift removeEmployeeFromShift(int workDayId, Employee employee) {
		/**
		 * Takes 2 identifying integers to find the corresponding shift in the shift repository to remove the corresponding employee from the shift
		 * @param workDayId An integer equal to the unique value corresponding to a shift
		 * @param employeeId An integer equal to the unique value corresponding to an employee
		 * @return shift The Shift object corresponding to the workDayId with the requested employee removed
		 */
		Shift shift = getShiftById(workDayId);
		shift.removeEmployee(employee);
		shiftArgumentErrorTest(shift);
		return shift;
	}
	@Transactional
	public void deleteShift(int workDayId) {
		/**
		 * Takes an identifying integer to find the corresponding shift in the shift repository to delete it from the repository
		 * @param workDayId An integer equal to the unique value corresponding to a shift
		 */
		Shift shift = getShiftById(workDayId);
		shiftRepo.delete(shift);
		shift.delete();
	}



}

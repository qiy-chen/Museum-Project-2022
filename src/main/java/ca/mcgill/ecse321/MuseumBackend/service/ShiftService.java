package ca.mcgill.ecse321.MuseumBackend.service;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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
	public List<Shift> getShifts() {
		List<Shift> shifts = new ArrayList<>();
		Iterable<Shift> shiftsIterable = shiftRepo.findAll();
		for(Shift shift: shiftsIterable) {
			shifts.add(shift);
		}
		return shifts;
	}

	/**
	 * @author Samuel Faubert
	 * Takes an identifying integer to find its corresponding shift in the shift repository
	 * @param workDayId An integer equal to the unique value corresponding to a shift
	 * @return shift A Shift object from the shift repository that corresponds to the id
	 */
	@Transactional
	public Shift getShiftById(int workDayId) {
		Shift shift = shiftRepo.findShiftByWorkDayId(workDayId);
		if (shift == null) {
			throw new MuseumBackendException(HttpStatus.NOT_FOUND, "Shift not found.");
		}
		return shift;
	}
	/**
	 * @author Samuel Faubert
	 * Takes a Shift object to be saved to the shift repository
	 * @param shift A Shift object to be persisted
	 * @return shift The same Shift object after it has been persisted
	 */
	@Transactional
	public Shift createShift(Shift shift) {
		shiftArgumentErrorTest(shift);
		shift = shiftRepo.save(shift);
		return shift;
	}
	/**
	 * @author Samuel Faubert
	 * Takes an identifying integer and 2 Date objects to find its corresponding shift in the shift repository to change the start and end times of the shift
	 *  @param workDayId An integer equal to the unique value corresponding to a shift
	 *	@param startTimeValue A string object equal to the new starting time to be set for the shift
	 * 	@param endTimeValue A string equal to the new ending time to be set for the shift
	 * 	@return shift The Shift object corresponding to the workDayId with its start and end times now updated
	 */
	@Transactional
	public Shift changeShiftDate(int workDayId, String startTimeValue, String endTimeValue) {
		Shift shift = getShiftById(workDayId);
		shiftArgumentErrorTest(shift);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
		shift.setStartTime(LocalDateTime.parse(startTimeValue,formatter));
		shift.setEndTime(LocalDateTime.parse(endTimeValue,formatter));
		shiftArgumentErrorTest(shift);
		return shift;
	}



	/**
	 * @author Samuel Faubert
	 * Takes 2 identifying integers to find the corresponding shift in the shift repository to add the corresponding employee to the shift
	 * @param workDayId An integer equal to the unique value corresponding to a shift
	 * @param employeeId An integer equal to the unique value corresponding to an employee
	 * @return shift The Shift object corresponding to the workDayId with the requested employee added
	 */
	@Transactional
	public Shift addEmployeeToShift(int workDayId, int employeeId) {
		Shift shift = getShiftById(workDayId);
		shiftArgumentErrorTest(shift);
		Employee employee = employeeRepository.findEmployeeByPersonRoleId(employeeId);
		shift.addEmployee(employee);
		return shift;
	}

	/**
	 * @author Samuel Faubert
	 * Takes 2 identifying integers to find the corresponding shift in the shift repository to remove the corresponding employee from the shift
	 * @param workDayId An integer equal to the unique value corresponding to a shift
	 * @param employeeId An integer equal to the unique value corresponding to an employee
	 * @return shift The Shift object corresponding to the workDayId with the requested employee removed
	 */
	@Transactional
	public Shift removeEmployeeFromShift(int workDayId, int employeeId) {
		Shift shift = getShiftById(workDayId);
		shiftArgumentErrorTest(shift);
		Employee employee = employeeRepository.findEmployeeByPersonRoleId(employeeId);
		shift.removeEmployee(employee);
		return shift;
	}
	/**
	 * @author Samuel Faubert
	 * Takes an identifying integer to find the corresponding shift in the shift repository to delete it from the repository
	 * @param workDayId An integer equal to the unique value corresponding to a shift
	 */
	@Transactional
	public void deleteShift(int workDayId) {
		Shift shift = getShiftById(workDayId);
		shiftArgumentErrorTest(shift);
		shiftRepo.delete(shift);
		shift.delete();
	}



}

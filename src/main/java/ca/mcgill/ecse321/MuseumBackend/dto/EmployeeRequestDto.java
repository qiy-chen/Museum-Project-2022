package ca.mcgill.ecse321.MuseumBackend.dto;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

import ca.mcgill.ecse321.MuseumBackend.service.PersonService;
import ca.mcgill.ecse321.MuseumBackend.service.ShiftService;

public class EmployeeRequestDto {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private ShiftService shiftService;

	// attributes
	private String email;
	private int[] shiftIDs;
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPersonID() {
		return this.email;
	}
	
	public void setEmployeeShiftIDs(int[] shiftIDs) {
		this.shiftIDs = shiftIDs;
	}
	
	public int[] getEmployeeShifts() {
		return this.shiftIDs;
	}
	
	public Employee toModel() {
		Employee employee = new Employee();
		Person person = personService.getPersonByEmail(this.email);
		employee.setPerson(person);
		for (int shiftID: this.shiftIDs) {
			Shift shift = shiftService.getShiftById(shiftID);
			employee.addShift(shift);
		}
		return employee;
	}
}

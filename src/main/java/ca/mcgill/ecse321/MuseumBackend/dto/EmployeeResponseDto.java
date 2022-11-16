package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

public class EmployeeResponseDto {

	private int id;
	private PersonDto person;
	private List<ShiftResponseDto> shifts;
	
	public EmployeeResponseDto(Employee employee) {
		this.id = employee.getPersonRoleId();
		this.person = new PersonDto(employee.getPerson());
		for (Shift shift : employee.getShifts()) {
			this.shifts.add(new ShiftResponseDto(shift.getStartTime(),shift.getEndTime(),shift.getWorkDayId(),shift.getMuseum(),shift.getEmployees()));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PersonDto getPerson() {
		return person;
	}

	public void setPerson(PersonDto person) {
		this.person = person;
	}

	public List<ShiftResponseDto> getShifts() {
		return shifts;
	}

	public void setShifts(List<ShiftResponseDto> shifts) {
		this.shifts = shifts;
	}
	
}

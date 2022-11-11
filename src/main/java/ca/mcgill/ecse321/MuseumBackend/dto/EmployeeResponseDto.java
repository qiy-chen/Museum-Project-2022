package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

public class EmployeeResponseDto {

	private int id;
	private PersonDto person;
	private List<ShiftDto> shifts;
	
	public EmployeeResponseDto(Employee employee) {
		this.id = employee.getPersonRoleId();
		this.person = new PersonDto(employee.getPerson());
		for (Shift shift : employee.getShifts()) {
			this.shifts.add(new ShiftDto(shift.getStartTime(),shift.getEndTime(),shift.getWorkDayId(),shift.getMuseum()));
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

	public List<ShiftDto> getShifts() {
		return shifts;
	}

	public void setShifts(List<ShiftDto> shifts) {
		this.shifts = shifts;
	}
	
}

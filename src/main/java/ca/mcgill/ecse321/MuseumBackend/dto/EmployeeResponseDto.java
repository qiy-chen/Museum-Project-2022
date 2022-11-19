package ca.mcgill.ecse321.MuseumBackend.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
/**
 * @Author Jeanine Looman
 */
public class EmployeeResponseDto {

	private int id;
	private PersonDto person;
	private List<ShiftResponseDto> shifts = new ArrayList<>();
	private String email;
	public EmployeeResponseDto() {
	}
	public EmployeeResponseDto(Employee employee) {
		this.id = employee.getPersonRoleId();
		this.person = new PersonDto(employee.getPerson());
		this.email = this.person.getEmail();
		for (Shift shift : employee.getShifts()) {
			this.shifts.add(new ShiftResponseDto(shift.getStartTime(),shift.getEndTime(),shift.getWorkDayId(),shift.getMuseum(),shift.getEmployees()));
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

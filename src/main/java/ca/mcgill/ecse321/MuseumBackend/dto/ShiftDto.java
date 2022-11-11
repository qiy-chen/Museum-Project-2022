package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

public class ShiftDto {

	private int workdayId;
	private Date startTime;
	private Date endTime;

	private Museum museum;
	private List<EmployeeResponseDto> employees;
	
	public ShiftDto(Date startTime, Date endTime, int workDayId, Museum museum, List<Employee> employees) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.workdayId = workDayId;
		this.museum = museum;
		for(Employee e : employees) {
			EmployeeResponseDto employee = new EmployeeResponseDto(e);
			this.employees.add(employee);
		}



	}
	public int getWorkDayId() {
		return workdayId;
	}

	public void setWorkdayId(int workdayId) {
		this.workdayId = workdayId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Museum getMuseum() {
		return museum;
	}

	public void setMuseum(Museum museum) {
		this.museum = museum;
	}
	public List<EmployeeResponseDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeResponseDto> employees) {
		this.employees = employees;
	}
}

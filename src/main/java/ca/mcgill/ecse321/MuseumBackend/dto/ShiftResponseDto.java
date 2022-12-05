package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftResponseDto {

	private int workDayId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private int museumId;
	private List<Integer> employeeIds = new ArrayList<>();
	
	public ShiftResponseDto() {

	}

	public ShiftResponseDto(LocalDateTime startTime, LocalDateTime endTime, int workDayId, int museumId, List<Employee> employees) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.workDayId = workDayId;
		this.museumId = museumId;
			for (Employee e : employees) {
				Integer employeeId = e.getPersonRoleId();
				this.employeeIds.add(employeeId);
			}
		}


	public int getWorkDayId() {
		return workDayId;
	}

	public void setWorkdayId(int workdayId) {
		this.workDayId = workdayId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public int getMuseumId() {
		return museumId;
	}

	public void setMuseumId(int museumId) {
		this.museumId = museumId;
	}
	public List<Integer> getEmployees() {
		return employeeIds;
	}

	public void setEmployees(List<Integer> employeeIds) {
		this.employeeIds = employeeIds;
	}
}
